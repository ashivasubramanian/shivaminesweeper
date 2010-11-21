package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;
import home.minesweeper.board.Cell;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;

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
					{new Cell(4, false, 0, 0), new Cell(0, false, 0, 1), new Cell(0, false, 0, 2), new Cell(5, false, 0, 3), new Cell(0, false, 0, 4),
						new Cell(0, false, 0, 5), new Cell(0, false, 0, 6), new Cell(0, false, 0, 7), new Cell(0, false, 0, 8)},
					{new Cell(1, false, 1, 0), new Cell(2, false, 1, 1), new Cell(0, false, 1 ,2), new Cell(0, false, 1, 3), new Cell(0, false, 1, 4),
						new Cell(0, false, 1, 5), new Cell(0, false, 1, 6), new Cell(0, false, 1, 7), new Cell(0, false, 1, 8)},
					{new Cell(0, false, 2, 0), new Cell(0, false, 2, 1), new Cell(0, false, 2, 2), new Cell(0, false, 2, 3), new Cell(-1, false, 2, 4),
						new Cell(0, false, 2, 5), new Cell(0, false, 2, 6), new Cell(0, false, 2, 7), new Cell(0, false, 2, 8)},
					{new Cell(0, false, 3, 0), new Cell(0, false, 3, 1), new Cell(0, false, 3, 2), new Cell(0, false, 3, 3), new Cell(0, false, 3, 4),
						new Cell(0, false, 3, 5), new Cell(0, false, 3, 6), new Cell(0, false, 3, 7), new Cell(0, false, 3, 8)},
					{new Cell(0, false, 4, 0), new Cell(0, false, 4, 1), new Cell(0, false, 4, 2), new Cell(0, false, 4, 3), new Cell(0, false, 4, 4),
						new Cell(0, false, 4, 5), new Cell(0, false, 4, 6), new Cell(0, false, 4, 7), new Cell(0, false, 4, 8)},
					{new Cell(0, false, 5, 0), new Cell(0, false, 5, 1), new Cell(0, false, 5, 2), new Cell(0, false, 5, 3), new Cell(0, false, 5, 4),
						new Cell(0, false, 5, 5), new Cell(0, false, 5, 6), new Cell(0, false, 5, 7), new Cell(0, false, 5, 8)},
					{new Cell(1, false, 6, 0), new Cell(2, false, 6, 1), new Cell(3, false, 6, 2), new Cell(4, false, 6, 3), new Cell(5, false, 6, 4),
						new Cell(6, false, 6, 5), new Cell(7, false, 6, 6), new Cell(8, false, 6, 7), new Cell(9, false, 6, 8)},
					{new Cell(1, false, 7, 0), new Cell(2, false, 7, 1), new Cell(3, false, 7, 2), new Cell(4, false, 7, 3), new Cell(5, false, 7, 4),
						new Cell(0, false, 7, 5), new Cell(7, false, 7, 6), new Cell(8, false, 7, 7), new Cell(9, false, 7, 8)},
					{new Cell(0, false, 8, 0), new Cell(0, false, 8, 1), new Cell(0, false, 8, 2), new Cell(0, false, 8, 3), new Cell(0, false, 8, 4),
						new Cell(0, false, 8, 5), new Cell(0, false, 8, 6), new Cell(0, false, 8, 7), new Cell(3, false, 8, 8)}
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

		Mockito.when(request.getParameter("row")).thenReturn("2");
		Mockito.when(request.getParameter("column")).thenReturn("4");
		servlet.execute();
		try {
			byte mineCountAsByte[] = new byte[57];
			servlet.getInputStream().read(mineCountAsByte);
			String mineCountJSON = new String(mineCountAsByte);
			Assert.assertEquals("Mine count is not -1", "{\"mineCount\" : \"-1\", \"colour\" : \"\", \"x\" : \"2\", \"y\" : \"4\"}", mineCountJSON);
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
}
