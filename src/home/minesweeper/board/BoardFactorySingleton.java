package home.minesweeper.board;

import java.security.SecureRandom;

/** 
 * Creates one (and only one) instance of the <code>BoardFactory</code> class
 * and ensures that clients receive only that instance.
 * 
 * @author Shivasubramanian A
 */
final public class BoardFactorySingleton {

	/**
	 * Constructor that does nothing.
	 */
	private BoardFactorySingleton() {
		/*
		 * Does nothing.
		 */
	}

	/**
	 * the instance of <code>BoardFactory</code> that is returned repeatedly.
	 */
	private static BoardFactory INSTANCE
		= new BoardFactorySingleton().new BoardFactory();

	/**
	 * Used to obtain an instance of <code>BoardFactory</code> 
	 * @return an instance of <code>BoardFactory</code>
	 */
	public static BoardFactory getBoardFactory() {
		return INSTANCE;
	}
	
	/**
	 * 
	 * 
	 * @author Shivasubramanian A
	 */
	public class BoardFactory {
		
		private BoardFactory() {
			/*
			 * Does nothing.
			 */
		}
		
		/**
		 * Creates a new board.
		 *  
		 * @param mode an instance of <code>BoardModes</code> that indicates the
		 *             mode of the board.
		 */
		public Board createNewBoard(BoardModes mode) {
			Board board = new Board(mode);
			fillRequiredCellsWithMines(board, mode);
			fillNonMineCellsWithNumbers(board, mode);
			return board;
		}

			
		/**
		 * Randomly populates cells with mines
		 * 
		 * @param board an instance of <code>Board</code> which is to be
		 *              populated with mines.
		 * @param mode  the mode in which <code>board</code> has been generated.
		 */
		private void fillRequiredCellsWithMines(Board board, BoardModes mode) {
			SecureRandom random = new SecureRandom();
			int rowCount = mode.getRows();
			int columnCount = mode.getColumns();
			int mineCounter = 0;
			while (mineCounter < mode.getMineCount()) { 
				for (int i = 0; i < rowCount; i++) {
					for (int j = 0; j < columnCount; j++) {
						if (random.nextBoolean()) {
							board.getRows()[i][j] = -1;
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
		private void fillNonMineCellsWithNumbers(Board board, BoardModes mode){
			int rowCount = mode.getRows();
			int columnCount = mode.getColumns();
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < columnCount; j++) {
					if (board.getRows()[i][j] != -1) {
						board.getRows()[i][j]
						    = howManyMinesSurroundThisCell(board, i, j);
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
		private int howManyMinesSurroundThisCell(Board board, int currentRow,
				int currentColumn) {
			int mineCount = 0;
			if (currentRow == 0) {
				if (currentColumn == 0) {
					if (board.getRows()[currentRow][currentColumn + 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn + 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn] == -1) {
						mineCount++;
					}
				} else if (currentColumn == board.getRows()[0].length - 1) {
					if (board.getRows()[currentRow][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn] == -1) {
						mineCount++;
					}
				} else if (currentColumn < board.getRows()[0].length - 1) {
					if (board.getRows()[currentRow][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn + 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow][currentColumn + 1] == -1) {
						mineCount++;
					}
				}
			}
			
			if (currentRow == board.getRows().length - 1) {
				if (currentColumn == 0) {
					if (board.getRows()[currentRow - 1][currentColumn] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow - 1][currentColumn + 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow][currentColumn + 1] == -1) {
						mineCount++;
					}
				} else if (currentColumn == board.getRows()[0].length - 1) {
					if (board.getRows()[currentRow][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow - 1][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow - 1][currentColumn] == -1) {
						mineCount++;
					}
				} else if (currentColumn < board.getRows()[0].length - 1) {
					if (board.getRows()[currentRow][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow - 1][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow - 1][currentColumn] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow - 1][currentColumn + 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow][currentColumn + 1] == -1) {
						mineCount++;
					}
				}
			}
			
			if (currentRow > 0 && currentRow < board.getRows().length - 1) {
				if (currentColumn == 0) {
					if (board.getRows()[currentRow - 1][currentColumn] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow - 1][currentColumn + 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow][currentColumn + 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn + 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn] == -1) {
						mineCount++;
					}
				} else if (currentColumn == board.getRows()[0].length - 1) {
					if (board.getRows()[currentRow - 1][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow - 1][currentColumn] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn] == -1) {
						mineCount++;
					}
				} else if (currentColumn < board.getRows()[0].length - 1) {
					if (board.getRows()[currentRow - 1][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow - 1][currentColumn] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow - 1][currentColumn + 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow][currentColumn + 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn - 1] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn] == -1) {
						mineCount++;
					}
					if (board.getRows()[currentRow + 1][currentColumn + 1] == -1) {
						mineCount++;
					}
				}
			}
			return mineCount;
		}
	}
}
