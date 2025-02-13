package app.labs.board.event;

import app.labs.board.dao.BoardRepository;
import app.labs.notice.dao.NoticeRepository;
import app.labs.board.model.Board;
import app.labs.notice.model.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Async
@Transactional(readOnly = true)
@Component
public class BoardEventListener {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    public BoardEventListener(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @EventListener
    public void handleBoardReportedEvent(BoardReportedEvent boardReportedEvent) {
        int boardId = boardReportedEvent.getBoardId();
        createNotice(boardId, "BOARD_REPORT");
        log.info("{}번 board가 신고되었습니다.", boardId);
    }

    @EventListener
    public void handleBoardOffensiveEvent(BoardOffensiveEvent boardOffensiveEvent) {
        int boardId = boardOffensiveEvent.getBoardId();
        boolean boardReported = boardOffensiveEvent.isBoardReported();
        if (boardReported) {
            createNotice(boardId, "BOARD_HIDE_REPORT");
            log.info("{}번 board가 신고 누적으로 인해 숨김 처리되었습니다.", boardId);
        } else {
            createNotice(boardId, "BOARD_HIDE_FILTER");
            log.info("{}번 board가 필터링 결과, 숨김 처리 되었습니다.", boardId);
        }
    }

    private void createNotice(int boardId, String  noticeType) {
        Notice notice = new Notice();
        Board board = boardRepository.getBoardInfo(boardId);
        String memberId = board.getMemberId();
        String boardTitle = board.getBoardTitle();
        
        String content;
        switch (noticeType) {
            case "BOARD_REPORT":
                content = String.format("[%s] 공감행성의 글이 신고되었습니다.", boardTitle);
                break;
            case "BOARD_HIDE_REPORT":
                content = String.format("[%s] 공감행성의 글이 신고 누적으로 인해 숨김 처리되었습니다.", boardTitle);
                break;
            case "BOARD_HIDE_FILTER":
                content = String.format("[%s] 공감행성의 글이 부적절한 내용으로 인해 숨김 처리되었습니다.", boardTitle);
                break;
            default:
                content = String.format("[%s] 공감행성의 글에 대한 알림이 있습니다.", boardTitle);
        }
   
        notice.setMemberId(memberId);
        
        noticeRepository.saveNotice(noticeType, boardId, memberId);
    }
}