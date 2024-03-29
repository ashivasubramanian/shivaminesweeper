package home.minesweeper.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import builders.BoardBuilder;

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
				if (rows[i][j].getMineCount() == -1) mineCount++;
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
				if (rows[i][j].getMineCount() == -1) mineCount++;
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
				if (rows[i][j].getMineCount() == -1) mineCount++;
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

		try {
			Board board = new BoardBuilder(BoardModes.BEGINNER).withMineIn(2, 4).build();
			Cell rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for tile above",
					1, rowsAfterExec[1][4].getMineCount());
			assertEquals("tile calculation error for left tile",
					1, rowsAfterExec[2][3].getMineCount());
			assertEquals("tile calculation error for right tile",
					1, rowsAfterExec[2][5].getMineCount());
			assertEquals("tile calculation error for tile below",
					1, rowsAfterExec[3][4].getMineCount());
		} catch (SecurityException se) {
			se.printStackTrace();
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

		try {
			Board board = new BoardBuilder(BoardModes.BEGINNER).withMineIn(0, 0).build();
			Cell rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for right tile",
					1, rowsAfterExec[0][1].getMineCount());
			assertEquals("Mine calculation error for tile below",
					1, rowsAfterExec[1][0].getMineCount());
			assertEquals("Mine calculation error for below right tile",
					1, rowsAfterExec[1][1].getMineCount());
		} catch (SecurityException se) {
			se.printStackTrace();
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

		try {
			Board board = new BoardBuilder(BoardModes.BEGINNER).withMineIn(0, 8).build();
			Cell rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for left tile",
					1, rowsAfterExec[0][7].getMineCount());
			assertEquals("Mine calculation error for tile below",
					1, rowsAfterExec[1][8].getMineCount());
			assertEquals("Mine calculation error for below left tile",
					1, rowsAfterExec[1][7].getMineCount());
		} catch (SecurityException se) {
			se.printStackTrace();
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

		try {
			Board board = new BoardBuilder(BoardModes.BEGINNER).withMineIn(8, 0).build();
			Cell rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for left tile",
					1, rowsAfterExec[7][0].getMineCount());
			assertEquals("Mine calculation error for tile below",
					1, rowsAfterExec[7][1].getMineCount());
			assertEquals("Mine calculation error for below left tile",
					1, rowsAfterExec[8][1].getMineCount());
		} catch (SecurityException se) {
			se.printStackTrace();
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

		try {
			Board board = new BoardBuilder(BoardModes.BEGINNER).withMineIn(8, 8).build();
			Cell rowsAfterExec[][] = board.getRows();
			assertEquals("Mine calculation error for top left tile",
					1, rowsAfterExec[7][7].getMineCount());
			assertEquals("Mine calculation error for left tile",
					1, rowsAfterExec[8][7].getMineCount());
			assertEquals("Mine calculation error for top tile",
					1, rowsAfterExec[7][8].getMineCount());
		} catch (SecurityException se) {
			se.printStackTrace();
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
	
	@Test
	public void verifyBoardValidationReturnsTrueWhenAllMinesAreMarked() {

		try {
			Board board = new BoardBuilder(BoardModes.BEGINNER).withMineIn(0, 0).withMineIn(1, 0).withMineIn(2, 0).withMineIn(3, 0)
				.withMineIn(4, 0).withMineIn(5, 0).withMineIn(5, 1).withMineIn(6, 0).withMineIn(7, 0).withMineIn(8, 8).build();
			board.markTile(0, 0, board.getRows()[0][0].getMineCount());
			board.markTile(1, 0, board.getRows()[1][0].getMineCount());
			board.markTile(2, 0, board.getRows()[2][0].getMineCount());
			board.markTile(3, 0, board.getRows()[3][0].getMineCount());
			board.markTile(4, 0, board.getRows()[4][0].getMineCount());
			board.markTile(5, 0, board.getRows()[5][0].getMineCount());
			board.markTile(5, 1, board.getRows()[5][1].getMineCount());
			board.markTile(6, 0, board.getRows()[6][0].getMineCount());
			board.markTile(7, 0, board.getRows()[7][0].getMineCount());
			board.markTile(8, 8, board.getRows()[8][8].getMineCount());
			assertTrue("Validation logic is incorrect!!", board.validateCurrentBoardState());
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		}
	}
}
