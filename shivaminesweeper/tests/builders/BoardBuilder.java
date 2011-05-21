package builders;

import home.minesweeper.board.Board;
import home.minesweeper.board.BoardModes;
import home.minesweeper.board.Cell;

import org.springframework.test.util.ReflectionTestUtils;

public class BoardBuilder {

	private Board board;

	private Cell rows[][];

	public BoardBuilder(BoardModes mode) {
		board = new Board(mode);
		rows = new Cell[mode.getRows()][mode.getColumns()];
		for (int i = 0; i < mode.getRows(); i++) {
			for (int j = 0; j < mode.getColumns(); j++) {
				rows[i][j] = new Cell(0, false, i, j);
			}
		}
	}
	
	public BoardBuilder withMineIn(int x, int y) {
		Cell cell = new Cell(-1, false, x, y);
		rows[x][y] = cell;
		return this;
	}
	
	public Board build() {
		ReflectionTestUtils.setField(board, "rows", rows);
		return board;
	}
}
