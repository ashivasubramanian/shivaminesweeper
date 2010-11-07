package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	/**
	 * An instance of <code>HttpServletRequest</code> that represents the 
	 * request.
	 */
	HttpServletRequest request;
	
	/**
	 * An instance of <code>Logger</code> to log messages.
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	/**
	 * The <code>execute()</code> method of Struts 2. Determines the mode of
	 * the board requested and renders that board.
	 * 
	 * @return the page to move to once execution is done.
	 */
	public String execute() {
		
		logger.entering(this.getClass().getName(), "execute");
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
		
		Board board = new Board(boardMode);
		request.getSession().setAttribute("board", board);
		logger.log(Level.INFO, board.printBoard());
		logger.exiting(this.getClass().getName(), "execute");
		return result;
	}
	
	/**
	 * Setter method to set the request instance.
	 * 
	 * @param req the request instance to be set.
	 */
	public void setServletRequest(HttpServletRequest req) {
		request = req;
	}

}
