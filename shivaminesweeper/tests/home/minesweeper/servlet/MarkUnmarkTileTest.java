package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;
import home.minesweeper.board.Cell;
import home.minesweeper.servlet.MarkUnmarkTile;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class MarkUnmarkTileTest {
	
	Board board;
	HttpServletRequest request;
	HttpSession session;
	
	@Before
	public void setUp() {
		//Setup data
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
