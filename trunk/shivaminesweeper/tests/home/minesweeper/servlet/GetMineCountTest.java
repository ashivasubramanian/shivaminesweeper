package home.minesweeper.servlet;

import static org.junit.Assert.assertEquals;
import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import builders.BoardBuilder;

public class GetMineCountTest {
	
	Board board;
	HttpServletRequest request;
	HttpSession session;
	HttpServletResponse response;
	
	@Before
	public void setUp() {
		//setup our data
		try {
			board = new BoardBuilder(BoardModes.BEGINNER).withMineIn(0, 0).withMineIn(0, 1).withMineIn(0, 2)
				.withMineIn(0, 8).withMineIn(2, 4).withMineIn(3, 0).withMineIn(3, 1).withMineIn(3, 2)
				.withMineIn(4, 0).withMineIn(4, 5).withMineIn(4, 6).withMineIn(4, 7).withMineIn(4, 8)
				.withMineIn(5, 5).withMineIn(6, 5).withMineIn(7, 5).withMineIn(8, 5)
				.withMineIn(6, 0).withMineIn(6, 1).withMineIn(7, 0).withMineIn(8, 0).withMineIn(8, 1).build();
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
		Mockito.when(request.getParameter("column")).thenReturn("3");
		servlet.execute();
		try {
			byte mineCountAsByte[] = new byte[68];
			servlet.getInputStream().read(mineCountAsByte);
			String mineCountJSON = new String(mineCountAsByte);
			assertEquals("Mine count is not 1", "{\"mineCount\" : \"1\", \"colour\" : \"green\", \"row\" : \"0\", \"column\" : \"3\"}", mineCountJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		Mockito.when(request.getParameter("row")).thenReturn("1");
		Mockito.when(request.getParameter("column")).thenReturn("8");
		servlet.execute();
		try {
			byte mineCountAsByte[] = new byte[68];
			servlet.getInputStream().read(mineCountAsByte);
			String mineCountJSON = new String(mineCountAsByte);
			Assert.assertEquals("Mine count is not 3", "{\"mineCount\" : \"1\", \"colour\" : \"green\", \"row\" : \"1\", \"column\" : \"8\"}", mineCountJSON);
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
		Mockito.when(request.getParameter("row")).thenReturn("6");
		Mockito.when(request.getParameter("column")).thenReturn("7");
		servlet.execute();
		byte actualJSON[] = new byte[401];
		try {
			servlet.getInputStream().read(actualJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		String expectedJSON = "{\"contiguous\": [";
		expectedJSON += "{\"mineCount\" : \"0\", \"colour\" : \"\", \"row\" : \"6\", \"column\" : \"7\"}," +
						"{\"mineCount\" : \"0\", \"colour\" : \"\", \"row\" : \"6\", \"column\" : \"8\"},";
		expectedJSON += "{\"mineCount\" : \"0\", \"colour\" : \"\", \"row\" : \"7\", \"column\" : \"7\"}," +
						"{\"mineCount\" : \"0\", \"colour\" : \"\", \"row\" : \"7\", \"column\" : \"8\"},";
		expectedJSON += "{\"mineCount\" : \"0\", \"colour\" : \"\", \"row\" : \"8\", \"column\" : \"7\"}," +
						"{\"mineCount\" : \"0\", \"colour\" : \"\", \"row\" : \"8\", \"column\" : \"8\"}";
		expectedJSON += "]}";
		assertEquals("Empty tiles JSON is incorrect!!", expectedJSON, new String(actualJSON));
	}
	
	@Test
	public void colourShouldBeGreenIfMineCountIs1Or2() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);
		Mockito.when(request.getParameter("row")).thenReturn("1");
		Mockito.when(request.getParameter("column")).thenReturn("0");
		servlet.execute();
		byte mineCount[] = new byte[68];
		try {
			servlet.getInputStream().read(mineCount);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		assertEquals("Colour is not green.", "{\"mineCount\" : \"2\", \"colour\" : \"green\", \"row\" : \"1\", \"column\" : \"0\"}", new String(mineCount));
	}
	
	@Test
	public void colourShouldBeOrangeIfMineCountIs3() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);
		Mockito.when(request.getParameter("row")).thenReturn("1");
		Mockito.when(request.getParameter("column")).thenReturn("1");
		servlet.execute();
		byte mineCountJSON[] = new byte[69];
		try {
			servlet.getInputStream().read(mineCountJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		assertEquals("Colour is not orange.", "{\"mineCount\" : \"3\", \"colour\" : \"orange\", \"row\" : \"1\", \"column\" : \"1\"}", new String(mineCountJSON));
	}
	
	@Test
	public void colourShouldBeRedIfMineCountIs4OrGreaterThan4() {
		GetMineCount servlet = new GetMineCount();
		servlet.setServletRequest(request);
		servlet.setServletResponse(response);
		Mockito.when(request.getParameter("row")).thenReturn("4");
		Mockito.when(request.getParameter("column")).thenReturn("1");
		servlet.execute();
		byte mineCountJSON[] = new byte[66];
		try {
			servlet.getInputStream().read(mineCountJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		assertEquals("Colour is not red.", "{\"mineCount\" : \"4\", \"colour\" : \"red\", \"row\" : \"4\", \"column\" : \"1\"}", new String(mineCountJSON));
		
		Mockito.when(request.getParameter("row")).thenReturn("7");
		Mockito.when(request.getParameter("column")).thenReturn("1");
		servlet.execute();
		try {
			servlet.getInputStream().read(mineCountJSON);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		assertEquals("Colour is not red for minecount > 4.",
				"{\"mineCount\" : \"5\", \"colour\" : \"red\", \"row\" : \"7\", \"column\" : \"1\"}", new String(mineCountJSON));
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
