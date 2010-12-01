package home.minesweeper.board;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
	private Cell[][] rows;

	/** 
	 * Stores the list of tiles that the user has marked. Marking means that the
	 * user believes that the tiles hide mines. Each key of the Map is of the
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
		if(mode == null) {
			throw new IllegalArgumentException(
				"Mode passed in as parameter cannot be null!!");
		}
		this.mode = mode;
		this.rows = new Cell[mode.getRows()][mode.getColumns()];
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
		assert mode != null : "Mode is null in fillRequiredCellsWithMines.";
		SecureRandom random = new SecureRandom();
		int rowCount = mode.getRows();
		int columnCount = mode.getColumns();
		int mineCounter = 0;
		while (mineCounter < mode.getMineCount()) { 
			int row = random.nextInt(mode.getRows());
			int column = random.nextInt(mode.getColumns());
			if (!rows[row][column] instanceof Cell) {
				rows[row][column] = new Cell(-1, false, row, column);
				mineCounter++;
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
	private void fillNonMineCellsWithNumbers(BoardModes mode) {
		assert mode != null : "Mode is null in fillNonMineCellsWithNumbers.";
		int rowCount = mode.getRows();
		int columnCount = mode.getColumns();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (null == rows[i][j] || rows[i][j].getMineCount() != -1) {
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
	private Cell howManyMinesSurroundThisCell(int currentRow,
			int currentColumn) {
		assert currentRow > -1 : "Row is negative in howManyMinesSurroundThisCell";
		assert currentColumn > -1 : "Column is negative in howManyMinesSurroundThisCell";
		int sumOfBombs = 0;
		int rowIndex = mode.getRows() - 1;
		int columnIndex = mode.getColumns() - 1;
		Cell cell = null;
		//Do cells on either side of the current cell have bombs?
		if (currentColumn > 0) {
			cell = rows[currentRow][currentColumn - 1];
			sumOfBombs += (cell != null && cell.getMineCount() == -1? 1 : 0);
		}
		if (currentColumn < columnIndex) {
			cell = rows[currentRow][currentColumn + 1];
			sumOfBombs += (cell != null && cell.getMineCount() == -1? 1 : 0);
		}
		
		//Do cells to the upper left, upper right and directly above our cell
		//have bombs?
		if (currentRow > 0) {
			if (currentColumn > 0) {
				cell = rows[currentRow - 1][currentColumn - 1];
				sumOfBombs +=
					(cell != null && cell.getMineCount() == -1? 1 : 0);
			}
			cell = rows[currentRow - 1][currentColumn];
			sumOfBombs += cell != null && cell.getMineCount() == -1? 1 : 0;
			if (currentColumn < columnIndex) {
				cell = rows[currentRow - 1][currentColumn + 1];
				sumOfBombs +=
					(cell != null && cell.getMineCount() == -1? 1 : 0);
			}
		}
		
		//Do cells to the bottom left, bottom right and directly below our cell
		//have bombs?
		if (currentRow < rowIndex) {
			if (currentColumn > 0) {
				cell = rows[currentRow + 1][currentColumn - 1];
				sumOfBombs += (cell != null && cell.getMineCount() == -1? 1 : 0);
			}
			cell = rows[currentRow + 1][currentColumn];
			sumOfBombs += cell != null && cell.getMineCount() == -1? 1 : 0;
			if (currentColumn < columnIndex) {
				cell = rows[currentRow + 1][currentColumn + 1];
				sumOfBombs +=
					(cell != null && cell.getMineCount() == -1 ? 1 : 0);
			}
		}
		return new Cell(sumOfBombs, false, currentRow, currentColumn);
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
		if (row > -1) {
			throw new IllegalArgumentException("Row cannot be negative!!");
		}
		if (column > -1) {
			throw new IllegalArgumentException("Column cannot be negative!!");
		}
		return rows[row][column].getMineCount();
	}

	/**
	 * Determines if the user correctly marked the mines. If yes, the method
	 * returns <code>true</code> and the game ends, otherwise returns
	 * <code>false</code>.
	 */
	public boolean validateCurrentBoardState() {
		Collection<Integer> values = markedTiles.values();
		for (int value : values) {
			if (value != -1) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * If <code>revealCell()</code> returns 0, then that cell may have
	 * contiguous empty cells. This method determines those cells and returns an
	 * array of integers that represent each empty cell.
	 */
	public Set<Cell> determineContiguousEmptyCells(int currentRow, int currentColumn) {
		System.out.println(this);
		Cell cell = rows[currentRow][currentColumn];
		Set<Cell> cells = new LinkedHashSet<Cell>();
		cells.add(cell);
		Set<Cell> contiguousCells = determineContiguousEmptyCells(cells, cells);
		System.out.println("contiguousCells: " + contiguousCells);
		return contiguousCells;
	}

	private Set<Cell> determineContiguousEmptyCells(Set<Cell> cellsWithValueZeroFromPreviousIteration, Set<Cell> allCellsWithValueZero) {
		for (Cell individualCell : cellsWithValueZeroFromPreviousIteration) {
			Set<Cell> set = new LinkedHashSet<Cell>();
			int rowIndex = mode.getRows() - 1;
			int columnIndex = mode.getColumns() - 1;
			int currentRow = getLocationOfCell(individualCell)[0];
			int currentColumn = getLocationOfCell(individualCell)[1];
			System.out.println("about to process " + cellsWithValueZeroFromPreviousIteration);
			System.out.println("processing " + currentRow + " " + currentColumn);
			boolean addedCell = false;
			//Do cells on either side of the current cell have bombs?
			if (currentColumn > 0) {
				Cell left = rows[currentRow][currentColumn - 1];
				System.out.println("left (" + currentRow + "," + (currentColumn - 1)
						+ ") => " + left.getMineCount());
				if (left.getMineCount() == 0 && !allCellsWithValueZero.contains(left)) {
						set.add(left);
						allCellsWithValueZero.add(left);
						addedCell = true;
				}
			}
			if (currentColumn < columnIndex) {
				Cell right = rows[currentRow][currentColumn + 1];
				System.out.println("right (" + currentRow + "," + (currentColumn + 1)
						+ ") => " + right.getMineCount());
				if (right.getMineCount() == 0 && !allCellsWithValueZero.contains(right)) {
						set.add(right);
						allCellsWithValueZero.add(right);
						addedCell = true;
				}
			}
			//Do cells to the upper left, upper right and directly above our cell
			//have bombs?
			if (currentRow > 0) {
				if (currentColumn > 0) {
					Cell topLeft = rows[currentRow - 1][currentColumn - 1];
					System.out.println("top left (" + (currentRow - 1) + ","
							+ (currentColumn - 1) + ") => " + topLeft.getMineCount());
					if (topLeft.getMineCount() == 0 && !allCellsWithValueZero.contains(topLeft)) {
							set.add(topLeft);
							allCellsWithValueZero.add(topLeft);
							addedCell = true;
					}
				}
				Cell top = rows[currentRow - 1][currentColumn];
				System.out.println("top (" + (currentRow - 1) + "," + currentColumn
						+ ") => " + top.getMineCount());
				if (top.getMineCount() == 0 && !allCellsWithValueZero.contains(top)) {
						set.add(top);
						allCellsWithValueZero.add(top);
						addedCell = true;
				}
				if (currentColumn < columnIndex) {
					Cell topRight = rows[currentRow - 1][currentColumn + 1];
					System.out.println("top right (" + (currentRow - 1) + ","
									+ (currentColumn + 1) + ") => "
									+ topRight.getMineCount());
					if (topRight.getMineCount() == 0 && !allCellsWithValueZero.contains(topRight)) {
							set.add(topRight);
							allCellsWithValueZero.add(topRight);
							addedCell = true;
					}
				}
			}
			//Do cells to the bottom left, bottom right and directly below our cell
			//have bombs?
			if (currentRow < rowIndex) {
				if (currentColumn > 0) {
					Cell bottomLeft = rows[currentRow + 1][currentColumn - 1];
					System.out.println("bottom left (" + (currentRow + 1) + ","
							+ (currentColumn - 1) + ") => "
							+ bottomLeft.getMineCount());
					if (bottomLeft.getMineCount() == 0 && !allCellsWithValueZero.contains(bottomLeft)) {
							set.add(bottomLeft);
							allCellsWithValueZero.add(bottomLeft);
							addedCell = true;
					}
				}
				Cell bottom = rows[currentRow + 1][currentColumn];
				System.out.println("bottom (" + (currentRow + 1) + ","
						+ currentColumn + ") => " + bottom.getMineCount());
				if (bottom.getMineCount() == 0 && !allCellsWithValueZero.contains(bottom)) {
						set.add(bottom);
						allCellsWithValueZero.add(bottom);
						addedCell = true;
				}
				if (currentColumn < columnIndex) {
					Cell bottomRight = rows[currentRow + 1][currentColumn + 1];
					System.out.println("bottom right (" + (currentRow + 1) + ","
							+ (currentColumn + 1) + ") => "
							+ bottomRight.getMineCount());
					if (bottomRight.getMineCount() == 0 && !allCellsWithValueZero.contains(bottomRight)) {
							set.add(bottomRight);
							allCellsWithValueZero.add(bottomRight);
							addedCell = true;
					}
				}
			}
			System.out.println("next iter...");
			if (addedCell) {
				determineContiguousEmptyCells(set, allCellsWithValueZero);
			}
		}
		System.out.println("done with set...");
		return cellsWithValueZeroFromPreviousIteration;
	}

	/**
	 * Adds the tile specified by <code>row</code> and <code>column</code> to
	 * the marked tiles list.
	 * 
	 * @param row the row the tile is present in.
	 * @param column the column the tile is present in.
	 * @param tileValue the value of the cell behind the tile.
	 */
	public void markTile(int row, int column, int tileValue) {
		markedTiles.put(row + "," + column, tileValue);
	}

	/**
	 * Removes the tile specified by <code>row</code> and <code>column</code>
	 * from the marked tiles list.
	 * 
	 * @param row the row the tile is present in.
	 * @param column the column the tile is present in.
	 */
	public void unmarkTile(int row, int column) {
		markedTiles.remove(row + "," + column); 
	}
	
	/**
	 * Returns a rows array that contains the mine count for each tile.
	 * 
	 * @return the rows array
	 */
	public Cell[][] getRows() {
		return rows;
	}
	
	/**
	 * Returns a <code>Map</code> that lists those tiles that have been marked
	 * by the user as those that contain mines.
	 * 
	 * @return the tiles that have been marked by the user 
	 */
	public Map<String, Integer> getMarkedTiles() {
		return markedTiles;
	}
	
	/**
	 * Returns the mode in which the board has been rendered.
	 * 
	 * @return an instance of <code>BoardModes</code>
	 */
	public BoardModes getMode() {
		return mode;
	}
	
	public String toString() {
		String board = "";
		for (int i = 0; i < mode.getRows(); i++) {
			for (int j = 0; j < mode.getColumns(); j++) {
				Cell cell = rows[i][j];
				board += cell.getMineCount() + " ";
			}
			board += "\n";
		}
		return board;
	}
	
	private int[] getLocationOfCell(Cell cell) {
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length; j++) {
				Cell individualCell = rows[i][j];
				if (cell.equals(individualCell)) {
					return new int[] {i,j};
				}
			}
		}
		return new int[2];
	}
}
