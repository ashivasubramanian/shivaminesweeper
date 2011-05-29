package home.minesweeper.servlet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;

public class CreateBoardTest {
	
	@Mock
	private HttpServletRequest mockRequest;
	
	@Mock
	private HttpSession mockSession;

	private CreateBoard servlet;
	
	@Before
	public void setup() {
		initMocks(this);
		servlet = new CreateBoard();
		servlet.setServletRequest(mockRequest);
	}

	@Test
	public void shouldCreateBoardInSpecifiedMode() {
		String mode = "BEGINNER";
		when(mockRequest.getParameter("mode")).thenReturn(mode);
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getId()).thenReturn("1");
		
		servlet.execute();
		
		ArgumentCaptor<Board> captor = ArgumentCaptor.forClass(Board.class);
		verify(mockSession).setAttribute(Matchers.anyString(), captor.capture());
		assertEquals(BoardModes.BEGINNER, captor.getValue().getMode());
	}
	
	@Test
	public void shouldSetAllAvailableModesInRequestAttribute() {
		when(mockRequest.getParameter("mode")).thenReturn("INTERMEDIATE");
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getId()).thenReturn("1");
		
		servlet.execute();
		
		ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockRequest, times(2)).setAttribute(Matchers.anyString(), listCaptor.capture());
		List<String> modes = listCaptor.getAllValues().get(0);
		assertEquals(Arrays.asList("BEGINNER", "INTERMEDIATE", "ADVANCED"), modes);
	}
}
