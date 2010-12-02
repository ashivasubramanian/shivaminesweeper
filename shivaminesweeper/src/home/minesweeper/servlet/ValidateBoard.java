package home.minesweeper.servlet;

import home.minesweeper.board.Board;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class ValidateBoard implements ServletRequestAware {
	
	private HttpServletRequest request;
	
	private InputStream inputStream;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public String execute() {
		logger.entering(this.getClass().getName(), "execute");
		Board board = (Board) request.getSession().getAttribute(request.getRequestedSessionId());
		String json = "";
		if (board.validateCurrentBoardState()) {
			json = "{\"validity\" : true}";
		} else {
			json = "{\"validity\" : false}";
		}
		inputStream = new ByteArrayInputStream(json);
		logger.info(json);
		logger.exiting(this.getClass().getName(), "execute");
		return ActionSupport.SUCCESS;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public InputStream getInputStream() {
		return this.inputStream;
	}

}
