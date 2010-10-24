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
		Integer mineCount = new Integer(cell.getMineCount());
		System.out.println("row : " + row + " col: " + column + " mine count : " + mineCount);
		response.setHeader("pragma", "no-cache");
		response.setDateHeader("expires", 0);
		inputStream = new StringBufferInputStream(mineCount.toString());
		return SUCCESS;
	}

}
