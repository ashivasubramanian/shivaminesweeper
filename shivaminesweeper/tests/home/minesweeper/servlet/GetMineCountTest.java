package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;
import home.minesweeper.board.Cell;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GetMineCountTest {
	
	Board board;
	HttpServletRequest request;
	HttpSession session;
	HttpServletResponse response;
	
	@Before
	public void setUp() {
		//setup our data
		try {
			board = new Board(BoardModes.BEGINNER);
			Cell rows[][] = {
					{new Cell(4, false), new Cell(0, false), new Cell(0, false), new Cell(5, false), new Cell(0, false),
						new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
					{new Cell(1, false), new Cell(2, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
						new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
					{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(-1, false),
						new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
					{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
						new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
					{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
						new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
					{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
						new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
					{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
						new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
					{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
						new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false)},
					{new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(0, false),
						new Cell(0, false), new Cell(0, false), new Cell(0, false), new Cell(3, false)}
			};
			Field field = board.getClass().getDeclaredField("rows");
			field.setAccessible(true);
			field.set(board, rows);
		} catch (NoSuchFieldException nsfe) {
			System.out.println("CRITICAL - rows attribute removed?");
			nsfe.printStackTrace();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		
		//setup some mock objects
		request = Mockito.mock(HttpServletRequest.class);
		session = Mockito.mock(HttpSession.class);
		response = Mockito.mock(HttpServletResponse.class);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getRequestedSessionId()).thenReturn("");
		Mockito.when(session.getAttribute("")).thenReturn(board);
	}

	@Test
	public void mineCountReturnedShouldBeForThatSpecificRowAndColumn() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);

		Mockito.when(request.getParameter("row")).thenReturn("0");
		Mockito.when(request.getParameter("column")).thenReturn("0");
		servlet.execute();
		try {
			byte mineCountAsByte[] = new byte[37];
			servlet.getInputStream().read(mineCountAsByte);
			String mineCount = new String(mineCountAsByte);
			Assert.assertEquals("Mine count is not 4", "{\"mineCount\" : \"4\", \"colour\" : \"red\"}", mineCount);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		Mockito.when(request.getParameter("row")).thenReturn("8");
		Mockito.when(request.getParameter("column")).thenReturn("8");
		servlet.execute();
		try {
			byte mineCountAsByte[] = new byte[40];
			servlet.getInputStream().read(mineCountAsByte);
			String mineCount = new String(mineCountAsByte);
			Assert.assertEquals("Mine count is not 3", "{\"mineCount\" : \"3\", \"colour\" : \"orange\"}", mineCount);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		Mockito.when(request.getParameter("row")).thenReturn("2");
		Mockito.when(request.getParameter("column")).thenReturn("4");
		servlet.execute();
		try {
			byte mineCountAsByte[] = new byte[35];
			servlet.getInputStream().read(mineCountAsByte);
			String mineCount = new String(mineCountAsByte);
			Assert.assertEquals("Mine count is not -1", "{\"mineCount\" : \"-1\", \"colour\" : \"\"}", mineCount);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Test
	public void cacheAttributesMustBeSetInTheResponse() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);

		Mockito.when(request.getParameter("row")).thenReturn("0");
		Mockito.when(request.getParameter("column")).thenReturn("0");
		servlet.execute();
		Mockito.verify(response).setHeader("pragma", "no-cache");
		Mockito.verify(response).setDateHeader("expires", 0);
	}
	
	@Test
	public void emptyStringMustBeReturnedForTilesWithZeroMineCount() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);
		Mockito.when(request.getParameter("row")).thenReturn("0");
		Mockito.when(request.getParameter("column")).thenReturn("1");
		servlet.execute();
		byte mineCount[] = new byte[18];
		try {
			servlet.getInputStream().read(mineCount);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		Assert.assertEquals("Value is not empty.", "{\"mineCount\" : \"\"}", new String(mineCount));
	}
	
	@Test
	public void colourShouldBeGreenIfMineCountIs1Or2() {
		
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);
		Mockito.when(request.getParameter("row")).thenReturn("1");
		Mockito.when(request.getParameter("column")).thenReturn("0");
		servlet.execute();
		byte mineCount[] = new byte[39];
		try {
			servlet.getInputStream().read(mineCount);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		Assert.assertEquals("Colour is not green.", "{\"mineCount\" : \"1\", \"colour\" : \"green\"}", new String(mineCount));
	}
}
