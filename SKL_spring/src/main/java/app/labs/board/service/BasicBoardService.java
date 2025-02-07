package app.labs.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.board.dao.BoardRepository;
import app.labs.board.model.Board;

@Service
public class BasicBoardService implements BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public List<Board> getBoardList(String boardCategory) {
		return boardRepository.getBoardList(boardCategory);
	}
	
	@Override
	public Board getBoardInfo(int boardId) {
		return boardRepository.getBoardInfo(boardId);
	}
	
	@Override
	public int createBoardId() {
		return boardRepository.createBoardId();
	}

	@Override
	public void createBoard(Board board) {
		boardRepository.createBoard(board);
	}
	
	@Override
	public void updateBoard(Board board) {
		boardRepository.updateBoard(board);
	}
}
