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
					{new Cell(4, 0, 0), new Cell(0, 0, 1), new Cell(0, 0, 2), new Cell(5, 0, 3), new Cell(0, 0, 4),
						new Cell(0, 0, 5), new Cell(0, 0, 6), new Cell(0, 0, 7), new Cell(-1, 0, 8)},
					{new Cell(1, 1, 0), new Cell(2, 1, 1), new Cell(0, 1, 2), new Cell(0, 1, 3), new Cell(0, 1, 4),
						new Cell(0, 1, 5), new Cell(0, 1, 6), new Cell(0, 1, 7), new Cell(0, 1, 8)},
					{new Cell(3, 2, 0), new Cell(4, 2, 1), new Cell(0, 2, 2), new Cell(0, 2, 3), new Cell(-1, 2, 4),
						new Cell(0, 2, 5), new Cell(0, 2, 6), new Cell(0, 2, 7), new Cell(0, 2, 8)},
					{new Cell(6, 3, 0), new Cell(0, 3, 1), new Cell(0, 3, 2), new Cell(0, 3, 3), new Cell(0, 3, 4),
						new Cell(0, 3, 5), new Cell(0, 3, 6), new Cell(0, 3, 7), new Cell(0, 3, 8)},
					{new Cell(0, 4, 0), new Cell(0, 4, 1), new Cell(0, 4, 2), new Cell(0, 4, 3), new Cell(0, 4, 4),
						new Cell(0, 4, 5), new Cell(0, 4, 6), new Cell(0, 4, 7), new Cell(0, 4, 8)},
					{new Cell(0, 5, 0), new Cell(0, 5, 1), new Cell(0, 5, 2), new Cell(0, 5, 3), new Cell(0, 5, 4),
						new Cell(0, 5, 5), new Cell(0, 5, 6), new Cell(0, 5, 7), new Cell(0, 5, 8)},
					{new Cell(1, 6, 0), new Cell(2, 6, 1), new Cell(3, 6, 2), new Cell(4, 6, 3), new Cell(5, 6, 4),
						new Cell(6, 6, 5), new Cell(7, 6, 6), new Cell(8, 6, 7), new Cell(9, 6, 8)},
					{new Cell(1, 7, 0), new Cell(2, 7, 1), new Cell(3, 7, 2), new Cell(4, 7, 3), new Cell(5, 7, 4),
						new Cell(0, 7, 5), new Cell(7, 7, 6), new Cell(8, 7, 7), new Cell(9, 7, 8)},
					{new Cell(0, 8, 0), new Cell(0, 8, 1), new Cell(0, 8, 2), new Cell(0, 8, 3), new Cell(0, 8, 4),
						new Cell(0, 8, 5), new Cell(0, 8, 6), new Cell(0, 8, 7), new Cell(3, 8, 8)}
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
			byte mineCountAsByte[] = new byte[59];
			servlet.getInputStream().read(mineCountAsByte);
			String mineCountJSON = new String(mineCountAsByte);
			Assert.assertEquals("Mine count is not 4", "{\"mineCount\" : \"4\", \"colour\" : \"red\", \"x\" : \"0\", \"y\" : \"0\"}", mineCountJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		Mockito.when(request.getParameter("row")).thenReturn("8");
		Mockito.when(request.getParameter("column")).thenReturn("8");
		servlet.execute();
		try {
			byte mineCountAsByte[] = new byte[62];
			servlet.getInputStream().read(mineCountAsByte);
			String mineCountJSON = new String(mineCountAsByte);
			Assert.assertEquals("Mine count is not 3", "{\"mineCount\" : \"3\", \"colour\" : \"orange\", \"x\" : \"8\", \"y\" : \"8\"}", mineCountJSON);
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
	public void contiguousEmptyTilesJSONMustBeReturnedForTilesWithZeroMineCount() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);
		Mockito.when(request.getParameter("row")).thenReturn("8");
		Mockito.when(request.getParameter("column")).thenReturn("0");
		servlet.execute();
		byte actualJSON[] = new byte[530];
		try {
			servlet.getInputStream().read(actualJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		String expectedJSON = "{\"contiguous\": [";
		expectedJSON += "{\"mineCount\" : \"0\", \"colour\" : \"\", \"x\" : \"8\", \"y\" : \"0\"},{\"mineCount\" : \"0\", \"colour\" : \"\", \"x\" : \"8\", \"y\" : \"1\"},";
		expectedJSON += "{\"mineCount\" : \"0\", \"colour\" : \"\", \"x\" : \"8\", \"y\" : \"2\"},{\"mineCount\" : \"0\", \"colour\" : \"\", \"x\" : \"8\", \"y\" : \"3\"},";
		expectedJSON += "{\"mineCount\" : \"0\", \"colour\" : \"\", \"x\" : \"8\", \"y\" : \"4\"},{\"mineCount\" : \"0\", \"colour\" : \"\", \"x\" : \"8\", \"y\" : \"5\"},";
		expectedJSON += "{\"mineCount\" : \"0\", \"colour\" : \"\", \"x\" : \"7\", \"y\" : \"5\"},{\"mineCount\" : \"0\", \"colour\" : \"\", \"x\" : \"8\", \"y\" : \"6\"},";
		expectedJSON += "{\"mineCount\" : \"0\", \"colour\" : \"\", \"x\" : \"8\", \"y\" : \"7\"}";
		expectedJSON += "]}";
		Assert.assertEquals("Empty tiles JSON is incorrect!!", expectedJSON, new String(actualJSON));
	}
	
	@Test
	public void colourShouldBeGreenIfMineCountIs1Or2() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);
		Mockito.when(request.getParameter("row")).thenReturn("1");
		Mockito.when(request.getParameter("column")).thenReturn("0");
		servlet.execute();
		byte mineCount[] = new byte[61];
		try {
			servlet.getInputStream().read(mineCount);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		Assert.assertEquals("Colour is not green.", "{\"mineCount\" : \"1\", \"colour\" : \"green\", \"x\" : \"1\", \"y\" : \"0\"}", new String(mineCount));
	}
	
	@Test
	public void colourShouldBeOrangeIfMineCountIs3() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);
		Mockito.when(request.getParameter("row")).thenReturn("2");
		Mockito.when(request.getParameter("column")).thenReturn("0");
		servlet.execute();
		byte mineCountJSON[] = new byte[62];
		try {
			servlet.getInputStream().read(mineCountJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		Assert.assertEquals("Colour is not orange.", "{\"mineCount\" : \"3\", \"colour\" : \"orange\", \"x\" : \"2\", \"y\" : \"0\"}", new String(mineCountJSON));
	}
	
	@Test
	public void colourShouldBeRedIfMineCountIs4OrGreaterThan4() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);
		Mockito.when(request.getParameter("row")).thenReturn("2");
		Mockito.when(request.getParameter("column")).thenReturn("1");
		servlet.execute();
		byte mineCountJSON[] = new byte[59];
		try {
			servlet.getInputStream().read(mineCountJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		Assert.assertEquals("Colour is not red.", "{\"mineCount\" : \"4\", \"colour\" : \"red\", \"x\" : \"2\", \"y\" : \"1\"}", new String(mineCountJSON));
		
		Mockito.when(request.getParameter("row")).thenReturn("3");
		Mockito.when(request.getParameter("column")).thenReturn("0");
		servlet.execute();
		try {
			servlet.getInputStream().read(mineCountJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		Assert.assertEquals("Colour is not red for minecount > 4.",
				"{\"mineCount\" : \"6\", \"colour\" : \"red\", \"x\" : \"3\", \"y\" : \"0\"}", new String(mineCountJSON));
	}
	
	@Test
	public void gameShouldEndWhenMineCountIsMinusOne() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);
		Mockito.when(request.getParameter("row")).thenReturn("0");
		Mockito.when(request.getParameter("column")).thenReturn("8");
		servlet.execute();
		byte gameOverJSON[] = new byte[24];
		try {
			servlet.getInputStream().read(gameOverJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		Assert.assertEquals("Game over JSON is incorrect!!", "{\"status\" : \"game_over\"}", new String(gameOverJSON));
	}
}
