package home.minesweeper.servlet;

import home.minesweeper.board.Board;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class MarkUnmarkTile extends ActionSupport implements ServletRequestAware {
	
	HttpServletRequest request;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private InputStream inputStream;
	
	public String markTile() {
		logger.entering(this.getClass().getName(), "execute");
		int row = Integer.parseInt(request.getParameter("row"));
		int column = Integer.parseInt(request.getParameter("column"));
		Board board = (Board) request.getSession().getAttribute(request.getRequestedSessionId());
		logger.log(Level.INFO, row + " " + column + " " + board.getRows()[row][column].getMineCount());
		board.markTile(row, column, board.getRows()[row][column].getMineCount());
		inputStream = new ByteArrayInputStream("{\"success\" : \"true\"}");
		logger.exiting(this.getClass().getName(), "execute");
		return SUCCESS;
	}

	public String unmarkTile() {
		logger.entering(this.getClass().getName(), "execute");
		int row = Integer.parseInt(request.getParameter("row"));
		int column = Integer.parseInt(request.getParameter("column"));
		Board board = (Board) request.getSession().getAttribute(request.getRequestedSessionId());
		logger.log(Level.INFO, row + " " + column);
		board.unmarkTile(row, column);
		inputStream = new ByteArrayInputStream("{\"success\" : \"true\"}");
		logger.exiting(this.getClass().getName(), "execute");
		return SUCCESS;
	}

	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
}
