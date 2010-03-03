package home.minesweeper.board;

import static org.junit.Assert.assertTrue;
import home.minesweeper.board.BoardFactorySingleton.BoardFactory;

import org.junit.Test;

public class BoardFactorySingletonTest {
	
	@Test
	public void repeatedCallsToCreateABoardFactoryMustReturnTheSameInstance() {
		BoardFactory factory = BoardFactorySingleton.getBoardFactory();
		BoardFactory factory2 = BoardFactorySingleton.getBoardFactory();
		assertTrue("Both the instances are not the same.", factory == factory2);
	}
}
