package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.Cell;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class GetMineCount extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private InputStream inputStream;

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
		System.out.println("in execute method...");
		Board board = (Board) request.getSession().getAttribute("board");
		int row = Integer.parseInt(request.getParameter("row"));
		int column = Integer.parseInt(request.getParameter("column"));
		Cell cell = board.getRows()[row][column];
		String mineCountJSON;
		if (cell.getMineCount() == 0) {
			mineCountJSON = "{\"mineCount\" : \"\"}";
		} else {
			mineCountJSON = "{\"mineCount\" : \"" + cell.getMineCount() + "\", \"colour\" : \"" + getColour(cell.getMineCount()) + "\"}";
		}
		System.out.println("row : " + row + " col: " + column + " mine count : " + mineCountJSON);
		response.setHeader("pragma", "no-cache");
		response.setDateHeader("expires", 0);
		inputStream = new StringBufferInputStream(mineCountJSON);
		return SUCCESS;
	}
	
	private String getColour(int mineCount) {
		String colour = "";
		if (mineCount == 1 || mineCount == 2) {
			colour = "green";
		} else if (mineCount == 3) {
			colour = "orange";
		} else if (mineCount >= 4) {
			colour = "red";
		}
		return colour;
	}

}
