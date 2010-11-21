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
	
	/**
	 * Determines whether the mine count is to be shown; if true, the mine count is shown, otherwise it is not.
	 */
	private boolean visible;
	
	private String colour;
	
	private int x;
	
	private int y;
	
	/**
	 * Initializes the cell with the mine count and display values. Note that <code>visible</code> determines whether
	 * the mine count is to be shown or not. This is usually set to <code>false</code> on page load, and set to
	 * <code>true</code> only when the user clicks on the cell.
	 * 
	 * @param mineCount the mine count.
	 * @param visible whether the mine count is to be shown on the page or not.
	 */
	public Cell(int mineCount, boolean visible, int x, int y) {
		this.mineCount = mineCount;
		this.visible = visible;
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

	/**
	 * Returns whether the mine count is to be shown or not.
	 * @return whether the mine count is to be shown or not.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets whether the mine count is to be shown or not.
	 * @param visible A <code>boolean</code> value that determines whether the mine count is to be shown or not.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
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
		mineCountJSON = "{\"mineCount\" : \"" + mineCount + "\", \"colour\" : \"" + colour + "\", \"x\" : \"" + x + "\", \"y\" : \"" + y + "\"}";
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
		if (cell.getMineCount() == mineCount && cell.getX() == x && cell.getY() == y) {
			return true;
		} else {
			return false;
		}
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
