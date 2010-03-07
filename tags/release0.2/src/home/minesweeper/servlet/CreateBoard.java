package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.BoardFactorySingleton;
import home.minesweeper.board.BoardModes;
import home.minesweeper.board.BoardFactorySingleton.BoardFactory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;


/**
 * The <code>SetupBoard</code> class does the random generation of whether each
 * cell in MineSweeper holds a bomb or not. It then generates the values in the
 * other cells and stores them in the session.
 * 
 * @author Shivasubramanian
 *
 */
public class CreateBoard implements ServletRequestAware {
	
	HttpServletRequest request;
	
	public String execute() {
		
		String mode = request.getParameter("mode").toUpperCase();
		String result = "success";
		BoardModes boardMode = null;
		if (mode.equals(BoardModes.BEGINNER.toString())) {
			boardMode = BoardModes.BEGINNER;
		} else if (mode.equals(BoardModes.INTERMEDIATE.toString())) {
			boardMode = BoardModes.INTERMEDIATE;
		} else if (mode.equals(BoardModes.ADVANCED.toString())) {
			boardMode = BoardModes.ADVANCED;
		}
		
		List<String> modes = new ArrayList<String>();
		modes.add(BoardModes.BEGINNER.toString());
		modes.add(BoardModes.INTERMEDIATE.toString());
		modes.add(BoardModes.ADVANCED.toString());
		request.setAttribute("modes", modes);

		BoardFactory boardFactory = BoardFactorySingleton.getBoardFactory();
		Board board = boardFactory.createNewBoard(boardMode);
		request.getSession().setAttribute("board", board);
		return result;
	}
	
	public void setServletRequest(HttpServletRequest req) {
		request = req;
	}
	
	/**
	 * Given a current row and a current cell, determines which among the 8
	 * cells that surround the current cell have bombs. 
	 * 
	 * @param rows         an array that holds each row. This is required since
	 *                     the method may need to move to other rows to
	 *                     determine if cells that surround the current cell in
	 *                     those rows have bombs.
	 * @param current_row  the counter that indicates which is the current row
	 * @param current_cell the counter that indicates which is the current cell
	 *                     in <code>current_row</code>
	 * @return             the number of bombs that surround the current cell
	 */
	private char howManyBombsDoesThisCellHave(
			char rows[][], int current_row, int current_cell) {
		int sumOfBombs = 0;
		//Do cells on either side of the current cell have bombs?
		if (current_cell > 0) {
			sumOfBombs += rows[current_row][current_cell - 1] == 'B'? 1 : 0;
		}
		if (current_cell < 7) {
			sumOfBombs += rows[current_row][current_cell + 1] == 'B'? 1 : 0;
		}
		
		//Do cells to the upper left, upper right and directly above our cell
		//have bombs?
		if (current_row > 0) {
			if (current_cell > 0) {
				sumOfBombs +=
					rows[current_row - 1][current_cell - 1] == 'B'? 1 : 0;
			}
			sumOfBombs += rows[current_row - 1][current_cell] == 'B'? 1 : 0;
			if (current_cell < 7) {
				sumOfBombs +=
					rows[current_row - 1][current_cell + 1] == 'B'? 1 : 0;
			}
		}
		
		//Do cells to the bottom left, bottom right and directly below our cell
		//have bombs?
		if (current_row < 7) {
			if (current_cell > 0) {
				sumOfBombs +=
					rows[current_row + 1][current_cell - 1] == 'B'? 1 : 0;
			}
			sumOfBombs += rows[current_row + 1][current_cell] == 'B'? 1 : 0;
			if (current_cell < 7) {
				sumOfBombs +=
					rows[current_row + 1][current_cell + 1] == 'B'? 1 : 0;
			}
		}
		return Integer.toString(sumOfBombs).charAt(0);
	}
}
