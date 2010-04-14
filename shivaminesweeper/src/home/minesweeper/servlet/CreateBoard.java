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
}
