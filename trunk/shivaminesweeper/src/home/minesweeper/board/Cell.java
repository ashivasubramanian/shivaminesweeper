package home.minesweeper.board;

/**
 * The <code>Cell</code> class represents a cell on the game board. A cell on the board is formed by the intersection of
 * a row and a column. There is a <code>Cell</code> instance for every cell. Each <code>Cell</code> instance holds two
 * values: the mine count that will be displayed when the user clicks on the cell, and a boolean value that indicates
 * whether the value can be shown or not. 
 * 
 * @author Shivasubramanian
 */
public class Cell {
	
	/**
	 * The count of mines that surround this cell.
	 */
	private int mineCount;
	
	private String colour;
	
	private int x;
	
	private int y;
	
	/**
	 * Initializes the cell with the mine count and display values. Note that <code>visible</code> determines whether
	 * the mine count is to be shown or not. This is usually set to <code>false</code> on page load, and set to
	 * <code>true</code> only when the user clicks on the cell.
	 * 
	 * @param mineCount the mine count.
	 */
	public Cell(int mineCount, int x, int y) {
		this.mineCount = mineCount;
		colour = getColour(mineCount);
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the count of mines that surround this cell.
	 * @return the mine count
	 */
	public int getMineCount() {
		return mineCount;
	}

	private String getColour(int mineCount) {
		String colour = "";
		if (mineCount == 1 || mineCount == 2) {
			colour = "green";
		} else if (mineCount == 3) {
			colour = "orange";
		} else if (mineCount >= 4) {
			colour = "red";
		}
		return colour;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toJSON() {
		String mineCountJSON;
		mineCountJSON = "{\"mineCount\" : \"" + mineCount + "\", \"colour\" : \"" + colour + "\", \"row\" : \"" + x + "\", \"column\" : \"" + y + "\"}";
		return mineCountJSON;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!(object instanceof Cell)) {
			throw new IllegalArgumentException("Can compare only instances of home.minesweeper.board.Cell!!");
		}
		Cell cell = (Cell) object;
		return (cell.getMineCount() == mineCount && cell.getX() == x && cell.getY() == y);
	}
	
	@Override
	public int hashCode() {
		return x + y + mineCount;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ") => " + mineCount;
	}
}
