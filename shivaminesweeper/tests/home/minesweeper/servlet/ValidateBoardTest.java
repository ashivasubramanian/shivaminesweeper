package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;
import home.minesweeper.board.Cell;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ValidateBoardTest {

	Board board;
	HttpServletRequest request;
	HttpSession session;

	@Before
	public void setUp() {
		board = new Board(BoardModes.BEGINNER);
		Cell rows[][] = new Cell[][] {
			{new Cell(-1, 0, 0),new Cell(0, 0, 1),new Cell(0, 0, 2),new Cell(0, 0, 3),new Cell(0, 0, 4),new Cell(0, 0, 5),new Cell(0, 0, 6),new Cell(0, 0, 7),new Cell(0, 0, 8)},
			{new Cell(-1, 1, 0),new Cell(0, 1, 1),new Cell(0, 1, 2),new Cell(0, 1, 3),new Cell(0, 1, 4),new Cell(0, 1, 5),new Cell(0, 1, 6),new Cell(0, 1, 7),new Cell(0, 1, 8)},
			{new Cell(-1, 2, 0),new Cell(0, 2, 1),new Cell(0, 2, 2),new Cell(0, 2, 3),new Cell(0, 2, 4),new Cell(0, 2, 5),new Cell(0, 2, 6),new Cell(0, 2, 7),new Cell(0, 2, 8)},
			{new Cell(-1, 3, 0),new Cell(0, 3, 1),new Cell(0, 3, 2),new Cell(0, 3, 3),new Cell(0, 3, 4),new Cell(0, 3, 5),new Cell(0, 3, 6),new Cell(0, 3, 7),new Cell(0, 3, 8)},
			{new Cell(-1, 4, 0),new Cell(0, 4, 1),new Cell(0, 4, 2),new Cell(0, 4, 3),new Cell(0, 4, 4),new Cell(0, 4, 5),new Cell(0, 4, 6),new Cell(0, 4, 7),new Cell(0, 4, 8)},
			{new Cell(-1, 5, 0),new Cell(0, 5, 1),new Cell(0, 5, 2),new Cell(0, 5, 3),new Cell(0, 5, 4),new Cell(0, 5, 5),new Cell(0, 5, 6),new Cell(0, 5, 7),new Cell(0, 5, 8)},
			{new Cell(-1, 6, 0),new Cell(0, 6, 1),new Cell(0, 6, 2),new Cell(0, 6, 3),new Cell(0, 6, 4),new Cell(0, 6, 5),new Cell(0, 6, 6),new Cell(0, 6, 7),new Cell(0, 6, 8)},
			{new Cell(-1, 7, 0),new Cell(0, 7, 1),new Cell(0, 7, 2),new Cell(0, 7, 3),new Cell(0, 7, 4),new Cell(0, 7, 5),new Cell(0, 7, 6),new Cell(0, 7, 7),new Cell(0, 7, 8)},
			{new Cell(-1, 8, 0),new Cell(0, 8, 1),new Cell(0, 8, 2),new Cell(0, 8, 3),new Cell(0, 8, 4),new Cell(0, 8, 5),new Cell(0, 8, 6),new Cell(0, 8, 7),new Cell(0, 8, 8)}};
		try {
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);	
		} catch (NoSuchFieldException nsfe) {
			nsfe.printStackTrace();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
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
