package home.minesweeper.board;

import static org.junit.Assert.assertEquals;
import home.minesweeper.board.BoardFactorySingleton.BoardFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;


public class BoardFactoryTest {

	/**
	 * The board generated in beginner mode shall have at the max 10 mines.
	 */
	@Test
	public void beginnerModeShallHave10Mines() {
		Board board = BoardFactorySingleton.getBoardFactory()
			.createNewBoard(BoardModes.BEGINNER);
		int mineCount = 0;
		int rows[][] = board.getRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length; j++) {
				if (rows[i][j] == -1) mineCount++;
			}
		}
		assertEquals("Mine count is not 10", 10, mineCount);
	}

	/**
	 * The board generated in intermediate mode shall have at the max 40 mines.
	 */
	public void intermediateModeShallHave40Mines() {
		Board board = BoardFactorySingleton.getBoardFactory()
			.createNewBoard(BoardModes.INTERMEDIATE);
		int mineCount = 0 ;
		int rows[][] = board.getRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length; j++) {
				if (rows[i][j] == -1) mineCount++;
			}
		}
		assertEquals("Mine count is not 40", 40, mineCount);
	}

	/**
	 * The board generated in advanced mode shall have at the max 99 mines.
	 */
	public void advancedModeShallHave99Mines() {
		Board board = BoardFactorySingleton.getBoardFactory()
			.createNewBoard(BoardModes.ADVANCED);
		int mineCount = 0 ;
		int rows[][] = board.getRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length; j++) {
				if (rows[i][j] == -1) mineCount++;
			}
		}
		assertEquals("Mine count is not 99", 99, mineCount);
	}
	
	/**
	 * For a mine that is not present at the four corners of the board, the
	 * mine-count calculation logic should consider all the 8 tiles surrounding
	 * the mine.
	 */
	@Test
	public void verifyMineCountCalculationForCentralTile() {
		BoardFactory factory = BoardFactorySingleton.getBoardFactory();
		Board board = factory.createNewBoard(BoardModes.BEGINNER);
		int rows[][] = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, -1, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		
		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
			
			Method method = factory.getClass()
				.getDeclaredMethod("fillNonMineCellsWithNumbers", Board.class,
					BoardModes.class);
			method.setAccessible(true);
			method.invoke(factory, board, BoardModes.BEGINNER);
			
			int rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for tile above",
					1, rowsAfterExec[1][4]);
			assertEquals("tile calculation error for left tile",
					1, rowsAfterExec[2][3]);
			assertEquals("tile calculation error for right tile",
					1, rowsAfterExec[2][5]);
			assertEquals("tile calculation error for tile below",
					1, rowsAfterExec[3][4]);
		} catch (SecurityException se) {
			se.printStackTrace();
		} catch (NoSuchFieldException nsfe) {
			nsfe.printStackTrace();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (IllegalAccessException iace) {
			iace.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		}
	}
	
	/**
	 * For a mine present at the top-left of the board, the mine-count
	 * calculation logic should consider only the 3 tiles surrounding the mine.
	 */
	@Test
	public void verifyMineCountCalculationForTopLeftTile() {
		BoardFactory factory = BoardFactorySingleton.getBoardFactory();
		Board board = factory.createNewBoard(BoardModes.BEGINNER);
		int rows[][] = {
			{-1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		
		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
			
			Method method = factory.getClass()
				.getDeclaredMethod("fillNonMineCellsWithNumbers", Board.class,
					BoardModes.class);
			method.setAccessible(true);
			method.invoke(factory, board, BoardModes.BEGINNER);
			
			int rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for right tile",
					1, rowsAfterExec[0][1]);
			assertEquals("Mine calculation error for tile below",
					1, rowsAfterExec[1][0]);
			assertEquals("Mine calculation error for below right tile",
					1, rowsAfterExec[1][1]);
		} catch (SecurityException se) {
			se.printStackTrace();
		} catch (NoSuchFieldException nsfe) {
			nsfe.printStackTrace();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (IllegalAccessException iace) {
			iace.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		}
	}

	/**
	 * For a mine present at the top-right of the board, the mine-count
	 * calculation logic should consider only the 3 tiles surrounding the mine.
	 */
	@Test
	public void verifyMineCountCalculationForTopRightTile() {
		BoardFactory factory = BoardFactorySingleton.getBoardFactory();
		Board board = factory.createNewBoard(BoardModes.BEGINNER);
		int rows[][] = {
			{0, 0, 0, 0, 0, 0, 0, 0, -1}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		
		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
			
			Method method = factory.getClass()
				.getDeclaredMethod("fillNonMineCellsWithNumbers", Board.class,
					BoardModes.class);
			method.setAccessible(true);
			method.invoke(factory, board, BoardModes.BEGINNER);
			
			int rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for left tile",
					1, rowsAfterExec[0][7]);
			assertEquals("Mine calculation error for tile below",
					1, rowsAfterExec[1][8]);
			assertEquals("Mine calculation error for below left tile",
					1, rowsAfterExec[1][7]);
		} catch (SecurityException se) {
			se.printStackTrace();
		} catch (NoSuchFieldException nsfe) {
			nsfe.printStackTrace();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (IllegalAccessException iace) {
			iace.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		}
	}


	/**
	 * For a mine present at the bottom-left of the board, the mine-count
	 * calculation logic should consider only the 3 tiles surrounding the mine.
	 */
	@Test
	public void verifyMineCountCalculationForBottomLeftTile() {
		BoardFactory factory = BoardFactorySingleton.getBoardFactory();
		Board board = factory.createNewBoard(BoardModes.BEGINNER);
		int rows[][] = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{-1, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		
		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
			
			Method method = factory.getClass()
				.getDeclaredMethod("fillNonMineCellsWithNumbers", Board.class,
					BoardModes.class);
			method.setAccessible(true);
			method.invoke(factory, board, BoardModes.BEGINNER);
			
			int rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for left tile",
					1, rowsAfterExec[7][0]);
			assertEquals("Mine calculation error for tile below",
					1, rowsAfterExec[7][1]);
			assertEquals("Mine calculation error for below left tile",
					1, rowsAfterExec[8][1]);
		} catch (SecurityException se) {
			se.printStackTrace();
		} catch (NoSuchFieldException nsfe) {
			nsfe.printStackTrace();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (IllegalAccessException iace) {
			iace.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		}
	}

	/**
	 * For a mine present at the bottom-right of the board, the mine-count
	 * calculation logic should consider only the 3 tiles surrounding the mine.
	 */
	@Test
	public void verifyMineCountCalculationForBottomRightTile() {
		BoardFactory factory = BoardFactorySingleton.getBoardFactory();
		Board board = factory.createNewBoard(BoardModes.BEGINNER);
		int rows[][] = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, -1}
		};
		
		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
			
			Method method = factory.getClass()
				.getDeclaredMethod("fillNonMineCellsWithNumbers", Board.class,
					BoardModes.class);
			method.setAccessible(true);
			method.invoke(factory, board, BoardModes.BEGINNER);
			
			int rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for top left tile",
					1, rowsAfterExec[7][7]);
			assertEquals("Mine calculation error for left tile",
					1, rowsAfterExec[8][7]);
			assertEquals("Mine calculation error for top tile",
					1, rowsAfterExec[7][8]);
		} catch (SecurityException se) {
			se.printStackTrace();
		} catch (NoSuchFieldException nsfe) {
			nsfe.printStackTrace();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (IllegalAccessException iace) {
			iace.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		}
	}
}
