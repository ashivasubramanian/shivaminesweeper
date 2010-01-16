package home.minesweeper.board;

import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/** 
 * The <code>SetupBoard</code> class does the random generation of whether each
 * cell in MineSweeper holds a bomb or not. It then generates the values in the
 * other cells and stores them in the session.
 * @author Shivasubramanian
 */
public class BoardRenderer {
	
	/**
	 * Creates a new board.
	 *  
	 * @param request an instance of <code>HttpServletRequest</code> from which the mode is obtained.
	 */
	public Board createNewBoard(HttpServletRequest request){
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
										 */ }


											
												
												
												private BoardRenderer(){
												/*
												 * Does nothing.
												 */ }
}
