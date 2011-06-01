package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import builders.BoardBuilder;

public class ValidateBoardTest {

	Board board;
	HttpServletRequest request;
	HttpSession session;

	@Before
	public void setUp() {
		try {
			board = new BoardBuilder(BoardModes.BEGINNER).withMineIn(0, 0).withMineIn(1, 0).withMineIn(2, 0).withMineIn(3, 0).withMineIn(4, 0)
				.withMineIn(5, 0).withMineIn(6, 0).withMineIn(7, 0).withMineIn(8, 0).build();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		request = Mockito.mock(HttpServletRequest.class);
		session = Mockito.mock(HttpSession.class); 
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getRequestedSessionId()).thenReturn("1");
		Mockito.when(session.getAttribute("1")).thenReturn(board);
	}

	@Test
	public void validationShouldPassIfAllMinesAreMarkedCorrectly() {
		board.markTile(0,0,-1);
		board.markTile(1,0,-1);
		board.markTile(2,0,-1);
		board.markTile(3,0,-1);
		board.markTile(4,0,-1);
		board.markTile(5,0,-1);
		board.markTile(6,0,-1);
		board.markTile(7,0,-1);
		board.markTile(8,0,-1);
		ValidateBoard servlet = new ValidateBoard();
		servlet.setServletRequest(request);
		servlet.execute();
		try {
			byte json[] = new byte[19];
			servlet.getInputStream().read(json);
			Assert.assertEquals("JSON for successful validation is incorrect!!", "{\"validity\" : true}", new String(json));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Test
	public void validationShouldFailIfMinesAreMarkedIncorrectly() {
		board.markTile(0,0,-1);
		board.markTile(1,0,-1);
		board.markTile(2,0,-1);
		board.markTile(3,0,-1);
		board.markTile(4,0,-1);
		board.markTile(5,0,-1);
		board.markTile(6,0,-1);
		board.markTile(7,0,-1);
		board.markTile(8,1,0);
		ValidateBoard servlet = new ValidateBoard();
		servlet.setServletRequest(request);
		servlet.execute();
		try {
			byte json[] = new byte[20];
			servlet.getInputStream().read(json);
			Assert.assertEquals("JSON for unsuccessful validation is incorrect!!", "{\"validity\" : false}", new String(json));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
