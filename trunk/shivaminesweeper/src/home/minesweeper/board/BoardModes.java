package home.minesweeper.board;

/**
 * Represents a list of the various modes that a board can hold. Mostly used as
 * the parameter to the <code>Board</code> class constructor.
 * 
 * @author Shivasubramanian
 */
public enum BoardModes {
	
	BEGINNER(9, 9, 9),
	INTERMEDIATE(16, 16, 40),
	ADVANCED(16, 30, 99);

	private int width;
	private int height;
	private int mineCount;
	
	BoardModes(int width, int height, int mineCount) {
		this.width = width;
		this.height = height;
		this.mineCount = mineCount;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getMineCount() {
		return this.mineCount;
	}
}
