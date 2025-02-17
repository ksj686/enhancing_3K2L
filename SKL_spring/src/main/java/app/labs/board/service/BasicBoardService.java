package app.labs.board.service;

import java.util.List;

import app.labs.board.event.BoardCreateEvent;
import app.labs.board.event.BoardOffensiveEvent;
import app.labs.board.event.BoardReportedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import app.labs.board.dao.BoardRepository;
import app.labs.board.model.Board;


@Service
public class BasicBoardService implements BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Override
	public List<Board> getBoardList(String boardCategory) {
		return boardRepository.getBoardList(boardCategory);
	}

	@Override
	public int getBoardCount() {
		return boardRepository.getBoardCount();
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
		eventPublisher.publishEvent(new BoardCreateEvent(board));
		boardRepository.createBoard(board);
	}

	@Override
	public void reportBoard(int boardId) {
		eventPublisher.publishEvent(new BoardReportedEvent(boardId));
		boardRepository.reportBoard(boardId);

		// 누적 신고수 5회 이상은 숨김 처리 이벤트 발생
		int boardReport = getBoardReport(boardId);
        if (boardReport >= 5) {
            eventPublisher.publishEvent(new BoardOffensiveEvent(boardId, true));
            offensiveBoard(boardId);
        }
	}

	// 누적 신고수 조회
	@Override
	public int getBoardReport(int boardId) {
		return boardRepository.getBoardReport(boardId); 
	}

	@Override
	public void offensiveBoard(int boardId) {
		boardRepository.offensiveBoard(boardId);
	}
}
