package home.minesweeper.board;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class BoardTest {
	
	/**
	 * The board generated in beginner mode shall have at the max 10 mines.
	 */
	@Test
	public void beginnerModeShallHave10Mines() {
		Board board = new Board(BoardModes.BEGINNER);
		int mineCount = 0;
		Cell rows[][] = board.getRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length; j++) {
				if (rows[i][j].getTileValue() == -1) mineCount++;
			}
		}
		assertEquals("Mine count is not 10", 10, mineCount);
	}

	/**
	 * The board generated in intermediate mode shall have at the max 40 mines.
	 */
	@Test
	public void intermediateModeShallHave40Mines() {
		Board board = new Board(BoardModes.INTERMEDIATE);
		int mineCount = 0 ;
		Cell rows[][] = board.getRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length; j++) {
				if (rows[i][j].getTileValue() == -1) mineCount++;
			}
		}
		assertEquals("Mine count is not 40", 40, mineCount);
	}

	/**
	 * The board generated in advanced mode shall have at the max 99 mines.
	 */
	@Test
	public void advancedModeShallHave99Mines() {
		Board board = new Board(BoardModes.ADVANCED);
		int mineCount = 0 ;
		Cell rows[][] = board.getRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length; j++) {
				if (rows[i][j].getTileValue() == -1) mineCount++;
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
		Board board = new Board(BoardModes.BEGINNER);
		Cell rows[][] = {
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(-1, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)}
		};
		
		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
			
			Method method = board.getClass().getDeclaredMethod("fillNonMineCellsWithNumbers",BoardModes.class);
			method.setAccessible(true);
			method.invoke(board, BoardModes.BEGINNER);
			
			Cell rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for tile above",
					1, rowsAfterExec[1][4].getTileValue());
			assertEquals("tile calculation error for left tile",
					1, rowsAfterExec[2][3].getTileValue());
			assertEquals("tile calculation error for right tile",
					1, rowsAfterExec[2][5].getTileValue());
			assertEquals("tile calculation error for tile below",
					1, rowsAfterExec[3][4].getTileValue());
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
		Board board = new Board(BoardModes.BEGINNER);
		Cell rows[][] = {
			{new Cell(-1, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)}
		};

		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
			
			/*Method method = factory.getClass()
				.getDeclaredMethod("fillNonMineCellsWithNumbers", Board.class,
					BoardModes.class);*/
			Method method = board.getClass()
				.getDeclaredMethod("fillNonMineCellsWithNumbers",
					BoardModes.class);
			method.setAccessible(true);
			//method.invoke(factory, board, BoardModes.BEGINNER);
			method.invoke(board, BoardModes.BEGINNER);
			
			Cell rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for right tile",
					1, rowsAfterExec[0][1].getTileValue());
			assertEquals("Mine calculation error for tile below",
					1, rowsAfterExec[1][0].getTileValue());
			assertEquals("Mine calculation error for below right tile",
					1, rowsAfterExec[1][1].getTileValue());
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
		Board board = new Board(BoardModes.BEGINNER);
		Cell rows[][] = {
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(-1, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)}
		};
		
		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
			
			Method method = board.getClass()
				.getDeclaredMethod("fillNonMineCellsWithNumbers",
					BoardModes.class);
			method.setAccessible(true);
			method.invoke(board, BoardModes.BEGINNER);
			
			Cell rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for left tile",
					1, rowsAfterExec[0][7].getTileValue());
			assertEquals("Mine calculation error for tile below",
					1, rowsAfterExec[1][8].getTileValue());
			assertEquals("Mine calculation error for below left tile",
					1, rowsAfterExec[1][7].getTileValue());
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
		Board board = new Board(BoardModes.BEGINNER);
		Cell rows[][] = {
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(-1, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)}
		};
		
		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
			
			Method method = board.getClass()
				.getDeclaredMethod("fillNonMineCellsWithNumbers",
					BoardModes.class);
			method.setAccessible(true);
			method.invoke(board, BoardModes.BEGINNER);
			
			Cell rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for left tile",
					1, rowsAfterExec[7][0].getTileValue());
			assertEquals("Mine calculation error for tile below",
					1, rowsAfterExec[7][1].getTileValue());
			assertEquals("Mine calculation error for below left tile",
					1, rowsAfterExec[8][1].getTileValue());
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
		Board board = new Board(BoardModes.BEGINNER);
		Cell rows[][] = {
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
			{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
				new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(-1, false)}
		};
		
		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
			
			Method method = board.getClass()
				.getDeclaredMethod("fillNonMineCellsWithNumbers",
					BoardModes.class);
			method.setAccessible(true);
			method.invoke(board, BoardModes.BEGINNER);
			
			Cell rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for top left tile",
					1, rowsAfterExec[7][7].getTileValue());
			assertEquals("Mine calculation error for left tile",
					1, rowsAfterExec[8][7].getTileValue());
			assertEquals("Mine calculation error for top tile",
					1, rowsAfterExec[7][8].getTileValue());
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
	 * The board generated in beginner mode shall have 9 rows by 9 columns.
	 */
	@Test
	public void beginnerModeShouldBeA9By9Board() {
		Board board = new Board(BoardModes.BEGINNER);
		int rows = board.getRows().length;
		assertEquals("Rows does not equal 9.", rows, 9);
		int columns = board.getRows()[0].length;
		assertEquals("Columns does not equal 9", columns, 9);
	}
	
	/**
	 * The board generated in intermediate mode shall have 16 rows by 16 columns.
	 */
	@Test
	public void intermediateModeShouldBeA16By16Board() {
		Board board = new Board(BoardModes.INTERMEDIATE);
		int rows = board.getRows().length;
		assertEquals("Rows does not equal 16.", rows, 16);
		int columns = board.getRows()[0].length;
		assertEquals("Columns does not equal 16", columns, 16);
	}

	/**
	 * The board generated in advanced mode shall have 16 rows by 30 columns.
	 */
	@Test
	public void advancedModeShouldBeA16By30Board() {
		Board board = new Board(BoardModes.ADVANCED);
		int rows = board.getRows().length;
		assertEquals("Rows does not equal 16.", rows, 16);
		int columns = board.getRows()[0].length;
		assertEquals("Columns does not equal 30", columns, 30);
	}

	/**
	 * Marking a tile should add it to the marked tiles list.
	 */
	@Test
	public void markingATileShouldAddToTheMarkedTilesList() {
		Board board = new Board(BoardModes.BEGINNER);
		int markedTilesCount = board.getMarkedTiles().size();
		board.markTile(2, 2, 4);
		assertEquals("Tile not added to marked list.",
			markedTilesCount + 1, board.getMarkedTiles().size());
	}

	/**
	 * Unmarking a tile should remove it from the marked tiles list.
	 */
	@Test
	public void unmarkingATileShouldRemoveItFromTheMarkedTilesList() {
		Board board = new Board(BoardModes.BEGINNER);
		board.markTile(2, 2, 5);
		int markedTilesCount = board.getMarkedTiles().size();
		board.unmarkTile(2, 2);
		assertEquals("Tile not removed from marked list.",
			markedTilesCount - 1, board.getMarkedTiles().size());
	}
}
