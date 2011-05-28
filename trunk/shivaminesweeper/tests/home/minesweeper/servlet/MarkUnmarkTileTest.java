package home.minesweeper.servlet;

import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;
import home.minesweeper.board.Cell;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
					{new Cell(4, 0, 0), new Cell(0, 0, 1), new Cell(0, 0, 2), new Cell(5, 0, 3), new Cell(0, 0, 4),
						new Cell(0, 0, 5), new Cell(0, 0, 6), new Cell(0, 0, 7), new Cell(0, 0, 8)},
					{new Cell(1, 1, 0), new Cell(2, 1, 1), new Cell(0, 1, 2), new Cell(0, 1, 3), new Cell(0, 1, 4),
						new Cell(0, 1, 5), new Cell(0, 1, 6), new Cell(0, 1, 7), new Cell(0, 1, 8)},
					{new Cell(0, 2, 0), new Cell(0, 2, 1), new Cell(0, 2, 2), new Cell(0, 2, 3), new Cell(-1, 2, 4),
						new Cell(0, 2, 5), new Cell(0, 2, 6), new Cell(0, 2, 7), new Cell(0, 2, 8)},
					{new Cell(0, 3, 0), new Cell(0, 3, 1), new Cell(0, 3, 2), new Cell(0, 3, 3), new Cell(0, 3, 4),
						new Cell(0, 3, 5), new Cell(0, 3, 6), new Cell(0, 3, 7), new Cell(0, 3, 8)},
					{new Cell(0, 4, 0), new Cell(0, 4, 1), new Cell(0, 4, 2), new Cell(0, 4, 3), new Cell(0, 4, 4),
						new Cell(0, 4, 5), new Cell(0, 4, 6), new Cell(0, 4, 7), new Cell(0, 4, 8)},
					{new Cell(0, 5, 0), new Cell(0, 5, 1), new Cell(0, 5, 2), new Cell(0, 5, 3), new Cell(0, 5, 4),
						new Cell(0, 5, 5), new Cell(0, 5, 6), new Cell(0, 5, 7), new Cell(0, 5, 8)},
					{new Cell(0, 6, 0), new Cell(0, 6, 1), new Cell(0, 6, 2), new Cell(0, 6, 3), new Cell(0, 6, 4),
						new Cell(0, 6, 5), new Cell(0, 6, 6), new Cell(0, 6, 7), new Cell(0, 6, 8)},
					{new Cell(0, 7, 0), new Cell(0, 7, 1), new Cell(0, 7, 2), new Cell(0, 7, 3), new Cell(0, 7, 4),
						new Cell(0, 7, 5), new Cell(0, 7, 6), new Cell(0, 7, 7), new Cell(0, 7, 8)},
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
