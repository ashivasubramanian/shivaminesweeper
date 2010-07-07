package home.minesweeper.board;

/**
 * Represents a list of the various modes that a board can hold. Mostly used as
 * the parameter to the <code>Board</code> class constructor.
 * 
 * @author Shivasubramanian
 */
public enum BoardModes {
	
	BEGINNER(9, 9, 10),
	INTERMEDIATE(16, 16, 40),
	ADVANCED(16, 30, 99);

	
	/**
	 * Stores the no of rows in the board.
	 */
	private int rows;
	
	/**
	 * Stores the no of columns in the board.
	 */
	private int columns;
	
	/**
	 * Stores the no of mines on the board.
	 */
	private int mineCount;
	
	/**
	 * Constructor that initializes the mode.
	 * 
	 * @param rows the no of rows in the board
	 * @param columns the no of columns in the board
	 * @param mineCount the no of mines on the board
	 */
	BoardModes(int rows, int columns, int mineCount) {
		if (rows < 1) {
			throw new IllegalArgumentException(
				"Rows parameter cannot be less than 1.");
		}
		if (columns < 1) {
			throw new IllegalArgumentException(
				"Columns parameter cannot be less than 1.");
		}
		if (mineCount < 1) {
			throw new IllegalArgumentException(
				"Mine count parameter cannot be less than 1.");
		}
		this.rows = rows;
		this.columns = columns;
		this.mineCount = mineCount;
	}
	
	/**
	 * Returns the no of rows in the board.
	 * 
	 * @return the row count
	 */
	public int getRows() {
		return this.rows;
	}
	
	/**
	 * Returns the no of columns in the board.
	 * 
	 * @return the column count
	 */
	public int getColumns() {
		return this.columns;
	}
	
	/**
	 * Returns the no of mines on the board.
	 * 
	 * @return the mine count
	 */
	public int getMineCount() {
		return this.mineCount;
	}
}
