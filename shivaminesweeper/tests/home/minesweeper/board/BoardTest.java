package home.minesweeper.board;

import static org.junit.Assert.*;
import org.junit.Test;

public class BoardTest {

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
	 * The board generated in beginner mode shall have at the max 10 mines.
	 */
	@Test
	public void beginnerModeShallHaveNoMoreThan10Mines() {
		Board board = new Board(BoardModes.BEGINNER);
		int mineCount = 0 ;
		int rows[][] = board.getRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length; j++) {
				if (rows[i][j] == -1) mineCount++;
			}
		}
		assertEquals("Mine count is not 10", 10, mineCount);
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
	 * The board generated in intermediate mode shall have at the max 40 mines.
	 */
	@Test
	public void intermediateModeShallHaveNoMoreThan40Mines() {
		Board board = new Board(BoardModes.INTERMEDIATE);
		int mineCount = 0 ;
		int rows[][] = board.getRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length; j++) {
				if (rows[i][j] == -1) mineCount++;
			}
		}
		assertEquals("Mine count is not 40", mineCount, 40);
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
	 * The board generated in advanced mode shall have at the max 99 mines.
	 */
	@Test
	public void advancedModeShallHaveNoMoreThan99Mines() {
		Board board = new Board(BoardModes.ADVANCED);
		int mineCount = 0 ;
		int rows[][] = board.getRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length; j++) {
				if (rows[i][j] == -1) mineCount++;
			}
		}
		assertEquals("Mine count is not 99", mineCount, 99);
	}
	
	/**
	 * Marking a tile should add it to the marked tiles list.
	 */
	@Test
	public void markingATileShouldAddToTheMarkedTilesList() {
		Board board = new Board(BoardModes.BEGINNER);
		int markedTilesCount = board.getMarkedTiles().size();
		board.markTile("2", "2");
		assertEquals("Tile not added to marked list.",
			board.getMarkedTiles().size(), markedTilesCount + 1);
	}

	/**
	 * Unmarking a tile should remove it from the marked tiles list.
	 */
	@Test
	public void unmarkingATileShouldRemoveItFromTheMarkedTilesList() {
		Board board = new Board(BoardModes.BEGINNER);
		board.markTile("2", "2");
		int markedTilesCount = board.getMarkedTiles().size();
		board.unmarkTile("2", "2");
		assertEquals("Tile not added to marked list.",
			board.getMarkedTiles().size(), markedTilesCount - 1);
	}
}
