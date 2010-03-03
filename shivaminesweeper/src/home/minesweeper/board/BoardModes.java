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

	private int rows;
	private int columns;
	private int mineCount;
	
	BoardModes(int rows, int columns, int mineCount) {
		this.rows = rows;
		this.columns = columns;
		this.mineCount = mineCount;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getColumns() {
		return this.columns;
	}
	
	public int getMineCount() {
		return this.mineCount;
	}
}
