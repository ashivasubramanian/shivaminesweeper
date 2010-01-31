package home.minesweeper.board;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

/** 
 * Creates one (and only one) instance of the 
 * <code>BoardRenderer</code> class and ensures that
 * clients receive only that instance.
 * TODO must be initialized in a static initialization block.
 */
final public class BoardFactorySingleton {

	private BoardFactorySingleton() {
		/*
		 * Does nothing.
		 */
	}

	private static BoardFactory INSTANCE = new BoardFactorySingleton().new BoardFactory();

	public static BoardFactory getBoardFactory() {
		return INSTANCE;
	}
	
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
		/*
		Get the mode from the request, then get the maximum no of
		rows and columns, then generate the board, and set the board
		into the session.
		*/
			return null;
		}

			
		/**
		 * Randomly populates cells with bombs, if the <code>Random</code> instance returns true.
		 */
		private void fillRequiredCellsWithBombs(Random random, Board board){
		}

		/** 
		 * Determines which are the non-bomb cells and then fills up those cells with the number of bombs that are present in the 8 tiles that surround them. Internally calls <code>howManyBombsDoesThisCellHave(int,int)</code> to determine the bomb count.
		 */
		private void fillNonBombCellsWithNumbers(){
		}
											
		/** 
		 * Determines the count of bombs in the 8 cells that surround the cell denoted by <code>currentRow</code> and <code>currentColumn</code>. To be called only for non-bomb cells. Fails if called on a cell that holds a bomb.
		 */
		private void howManyBombsSurroundThisCell(int currentRow, int currentColumn){
		/*
		 * Determines if the cell is a non-bomb cell and if yes, determines the count of bombs in the 8 cells that surround it.
		 */
		}
	}
}
