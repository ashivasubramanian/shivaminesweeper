package home.minesweeper.board;

public class Cell {
	
	private int tileValue;
	
	private boolean visible;
	
	public Cell(int tileValue, boolean visible) {
		this.tileValue = tileValue;
		this.visible = visible;
	}

	public int getTileValue() {
		return tileValue;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
