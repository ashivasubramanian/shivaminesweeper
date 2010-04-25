package home.minesweeper.board;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

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
	private Map<String, Integer> markedTiles;
	
	/**
	 * Stores the mode in which the board is currently rendered in.
	 */
	private BoardModes mode;
	
	/**
	 * Constructs a <code>Board</code> instance and initializes the board.
	 * 
	 * @param mode One of the values of the <code>BoardModes</code> enum.
	 */
	public Board(BoardModes mode) {
		this.mode = mode;
		this.rows = new int[mode.getRows()][mode.getColumns()];
		markedTiles = new HashMap<String, Integer>();
		fillRequiredCellsWithMines(mode);
		fillNonMineCellsWithNumbers(mode);
	}

	/**
	 * Randomly populates cells with mines
	 * 
	 * @param board an instance of <code>Board</code> which is to be
	 *              populated with mines.
	 * @param mode  the mode in which <code>board</code> has been generated.
	 */
	private void fillRequiredCellsWithMines(BoardModes mode) {
		SecureRandom random = new SecureRandom();
		int rowCount = mode.getRows();
		int columnCount = mode.getColumns();
		int mineCounter = 0;
		while (mineCounter < mode.getMineCount()) { 
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < columnCount; j++) {
					if (random.nextBoolean()) {
						rows[i][j] = -1;
						mineCounter++;
						if (mineCounter == mode.getMineCount()) return;
					}
				}
			}
		}
	}

	/** 
	 * Determines which are the non-mine cells and then fills up those cells
	 * with the number of mines that are present in the 8 cells that
	 * surround them.
	 * 
	 * @param board an instance of <code>Board</code> which has been 
	 *              populated with mines and needs its non-mine cells to be
	 *              filled up.
	 * @param mode the mode in which <code>board</code> has been generated.
	 */
	private void fillNonMineCellsWithNumbers(BoardModes mode){
		int rowCount = mode.getRows();
		int columnCount = mode.getColumns();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (rows[i][j] != -1) {
					rows[i][j]
					    = howManyMinesSurroundThisCell(i, j);
				}
			}
		}
	}
										
	/** 
	 * Determines the count of mines in the 8 cells that surround the cell
	 * denoted by <code>currentRow</code> and <code>currentColumn</code>.
	 * 
	 * @param board an instance of <code>Board</code> which has been 
	 *              populated with mines and needs its non-mine cells to be
	 *              filled up.
	 * @param currentRow the row in which the cell resides
	 * @param currentColumn the column in which the cell resides
	 * @return the count of mines.
	 */
	private int howManyMinesSurroundThisCell(int currentRow,
			int currentColumn) {
		int sumOfBombs = 0;
		int rowIndex = mode.getRows() - 1;
		int columnIndex = mode.getColumns() - 1;
		//Do cells on either side of the current cell have bombs?
		if (currentColumn > 0) {
			sumOfBombs += rows[currentRow][currentColumn - 1] == -1? 1 : 0;
		}
		if (currentColumn < columnIndex) {
			sumOfBombs += rows[currentRow][currentColumn + 1] == -1? 1 : 0;
		}
		
		//Do cells to the upper left, upper right and directly above our cell
		//have bombs?
		if (currentRow > 0) {
			if (currentColumn > 0) {
				sumOfBombs +=
					rows[currentRow - 1][currentColumn - 1] == -1? 1 : 0;
			}
			sumOfBombs += rows[currentRow - 1][currentColumn] == -1? 1 : 0;
			if (currentColumn < columnIndex) {
				sumOfBombs +=
					rows[currentRow - 1][currentColumn + 1] == -1? 1 : 0;
			}
		}
		
		//Do cells to the bottom left, bottom right and directly below our cell
		//have bombs?
		if (currentRow < rowIndex) {
			if (currentColumn > 0) {
				sumOfBombs +=
					rows[currentRow + 1][currentColumn - 1] == -1? 1 : 0;
			}
			sumOfBombs += rows[currentRow + 1][currentColumn] == -1? 1 : 0;
			if (currentColumn < columnIndex) {
				sumOfBombs +=
					rows[currentRow + 1][currentColumn + 1] == -1 ? 1 : 0;
			}
		}
		return sumOfBombs;
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
	public void markTile(int row, int column, int tileValue) {
		markedTiles.put(row + "," + column, tileValue);
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
	public void unmarkTile(int row, int column) {
		markedTiles.remove(row + "," + column); 
	}
	
	public int[][] getRows() {
		return rows;
	}
	
	public Map<String, Integer> getMarkedTiles() {
		return markedTiles;
	}
	
	/**
	 * Returns the mode in which the board has been rendered.
	 * @return an instance of <code>BoardModes</code>
	 */
	public BoardModes getMode() {
		return mode;
	}
}