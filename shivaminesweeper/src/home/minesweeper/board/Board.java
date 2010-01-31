package home.minesweeper.board;

import java.util.List;

/**
 * Represents the board that is currently being played by the user.
 * 
 * @author Shivasubramanian
 */
public class Board {
	
	/**
	 * An array of arrays that represents each cell in the game. Each array item
	 * stores the value that is hidden by the tile. The type is <code>int</code>
	 * since the values hidden by the tiles are integers.
	 * 
	 * @uml.property name="rows" multiplicity="(0 -1)" dimension="2"
	 */
	private int[][] rows;
	/** 
	 * Stores the list of tiles that the user has marked. Marking means that the
	 * user believes that the tiles hide mines. Each entry in the list is of the
	 * form, "<code>row</code>,<code>column</code>".
	 * @uml.property name="markedTiles"
	 */
	private List<String> markedTiles;
	
	/**
	 * Constructs a <code>Board</code> instance and initializes the board.
	 * 
	 * @param mode One of the values of the <code>BoardModes</code> enum.
	 */
	public Board(BoardModes mode) {
		int width = mode.getWidth();
		int height = mode.getHeight();
		this.rows = new int[width][height];
	}

	/**
	 * Reveals what is in the cell determined by <code>row</code> and
	 * <code>column</code>. Return type is -1 if the cell held a bomb, otherwise
	 * the digit held is returned.
	 * 
	 * @param row the row the tile is present in.
	 * @param column the column the tile is present in.
	 * @return the contents of the cell.
	 */
	public int revealCell(int row, int column) {
		return rows[row][column];
	}

	/**
	 * Determines if the user correctly marked the bombs. If yes, the method
	 * returns <code>true</code> and the game ends, otherwise returns
	 * <code>false</code>.
	 */
	public boolean validateCurrentBoardState() {
		return false;
	}

	/**
	 * If <code>revealCell()</code> returns 0, then that cell may have
	 * contiguous empty cells. This method determines those cells and returns an
	 * array of integers that represent each empty cell.
	 */
	public int[][] determineContiguousEmptyCells(int currentRow,
			int currentColumn) {
		return null;
	}

	/**
	 * Adds the tile specified by <code>row</code> and <code>column</code> to
	 * the marked tiles list. The parameters are <code>String</code>s since the
	 * list stores values as "<code>row</code>,<code>column</code>", and on
	 * server submit, the values are <code>String</code>s anyway.
	 * 
	 * @param row the row the tile is present in.
	 * @param column the column the tile is present in.
	 */
	public void markTile(String row, String column) {
		markedTiles.add(row + "," + column); 
	}

	/**
	 * Removes the tile specified by <code>row</code> and <code>column</code>
	 * from the marked tiles list. The parameters are <code>String</code>s since
	 * the list stores values as "<code>row</code>,<code>column</code>", and on
	 * server submit, the values are <code>String</code>s anyway.
	 * 
	 * @param row the row the tile is present in.
	 * @param column the column the tile is present in.
	 */
	public void unmarkTile(String row, String column) {
		markedTiles.remove(row + "," + column); 
	}
	
	public int[][] getRows() {
		return rows;
	}
	
	public List<String> getMarkedTiles() {
		return markedTiles;
	}
}