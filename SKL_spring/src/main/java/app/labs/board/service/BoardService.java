package app.labs.board.service;

import java.util.List;

import app.labs.board.model.Board;

public interface BoardService {
	List<Board> getBoardList(String boardCategory);
	Board getBoardInfo(int boardId);
	int createBoardId();
	void createBoard(Board board);
	void updateBoard(Board board);
	// 삭제 기능은 없음 
}
