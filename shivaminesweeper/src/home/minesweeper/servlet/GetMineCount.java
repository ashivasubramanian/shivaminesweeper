package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.Cell;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class GetMineCount extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private InputStream inputStream;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public String execute() {
		logger.entering(this.getClass().getName(), "execute");
		logger.log(Level.INFO, "session id : " + request.getRequestedSessionId());
		Board board = (Board) request.getSession().getAttribute(request.getRequestedSessionId());
		int row = Integer.parseInt(request.getParameter("row"));
		int column = Integer.parseInt(request.getParameter("column"));
		Cell cell = board.getRows()[row][column];
		String json = "";
		if (cell.getMineCount() > 0) {
			 json = cell.toJSON();
		} else if (cell.getMineCount() == 0) {
			json = constructContiguousCellsJSON(board.determineContiguousEmptyCells(row, column));
		} else if (cell.getMineCount() < 0) {
			json = "{\"status\" : \"game_over\"}";
		}
		logger.log(Level.INFO, "row : " + row + " col: " + column + " mine count : " + json);
		response.setHeader("pragma", "no-cache");
		response.setDateHeader("expires", 0);
		inputStream = new ByteArrayInputStream(json.getBytes());
		logger.exiting(this.getClass().getName(), "execute");
		return SUCCESS;
	}
	
	private String constructContiguousCellsJSON(Set<Cell> contiguousCells) {
		String json = "";
		json = "{\"contiguous\": [";
		for (Cell individualCell : contiguousCells) {
			json += individualCell.toJSON() + ",";
		}
		json = json.substring(0, json.length() - 1);
		json += "]}";
		return json;
	}
}
