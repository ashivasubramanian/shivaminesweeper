package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import builders.BoardBuilder;

public class MarkUnmarkTileTest {
	
	Board board;
	HttpServletRequest request;
	HttpSession session;
	
	@Before
	public void setUp() {
		//Setup data
		try {
			board = new BoardBuilder(BoardModes.BEGINNER).withMineIn(2, 4).build();
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
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getRequestedSessionId()).thenReturn("");
		Mockito.when(session.getAttribute("")).thenReturn(board);
	}

	@Test
	public void markingATileShouldRunSuccessfully() {
		Mockito.when(request.getParameter("row")).thenReturn("6");
		Mockito.when(request.getParameter("column")).thenReturn("5");
		int originalMarkedTilesCount = board.getMarkedTiles().size();
		MarkUnmarkTile servlet = new MarkUnmarkTile();
		servlet.setServletRequest(request);
		servlet.markTile();
		Assert.assertEquals("Tile was not marked!!", originalMarkedTilesCount + 1, board.getMarkedTiles().size());
	}
	
	@Test
	public void unmarkingATileShouldRunSuccessfully() {
		Mockito.when(request.getParameter("row")).thenReturn("6");
		Mockito.when(request.getParameter("column")).thenReturn("5");
		MarkUnmarkTile servlet = new MarkUnmarkTile();
		servlet.setServletRequest(request);
		servlet.markTile();
		int markedTilesCount = board.getMarkedTiles().size();
		servlet.unmarkTile();
		Assert.assertEquals("Tile was not unmarked!!", markedTilesCount - 1, board.getMarkedTiles().size());
	}

}
