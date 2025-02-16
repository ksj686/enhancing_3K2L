package app.labs.board.event;

import app.labs.AiService;
import app.labs.board.service.BoardService;
import app.labs.notice.service.NoticeService;
import app.labs.board.model.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@Async
@Transactional(readOnly = true)
@Component
public class BoardEventListener {

    @Autowired
    private BoardService boardService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AiService aiService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public BoardEventListener(BoardService boardService) {
        this.boardService = boardService;
    }

    @EventListener
    public void handleBoardReportedEvent(BoardReportedEvent boardReportedEvent) {
        int boardId = boardReportedEvent.getBoardId();
        createNotice(boardId, "BOARD_REPORT");
        log.info("{}번 글이 신고되었습니다.", boardId);
    }

    @EventListener
    public void handleBoardCreateEvent(BoardCreateEvent boardCreateEvent) {
        Board board = boardCreateEvent.getBoard();
        String boardContent = board.getBoardContent();
        String extractedText = extractTextFromHtml(boardContent); // HTML에서 텍스트 추출

        log.info("추출된 텍스트: {}", extractedText); // 추출된 텍스트 출력

        String filterResult = aiService.filterService(boardContent);
        log.info("필터결과: {}", filterResult);
        double filterPercentage;
        try {
            filterPercentage = Double.parseDouble(filterResult);
        } catch (NumberFormatException e) {
            log.error("필터링 결과를 숫자로 변환하는 데 실패했습니다: {}", filterResult);
            return; // 예외 발생 시 메서드 종료
        }

        // 필터링 결과 숨김 처리 이벤트 발생
        if (filterPercentage > 0.55) {
            int boardId = board.getBoardId();
            eventPublisher.publishEvent(new BoardOffensiveEvent(boardId, false));
        }
    }

    @EventListener
    public void handleBoardOffensiveEvent(BoardOffensiveEvent boardOffensiveEvent) {
        int boardId = boardOffensiveEvent.getBoardId();
        boolean boardReported = boardOffensiveEvent.isBoardReported();
        if (boardReported) {
            createNotice(boardId, "BOARD_HIDE_REPORT");
            log.info("{}번 글의 신고 누적으로 인해 숨김 처리되었습니다.", boardId);
        } else {
            createNotice(boardId, "BOARD_HIDE_FILTER");
            boardService.offensiveBoard(boardId);
            log.info("{}번 글의 부적절한 내용으로 인해, 숨김 처리되었습니다.", boardId);
        }
    }

    private void createNotice(int boardId, String  noticeType) {
        Board board = boardService.getBoardInfo(boardId);
        String memberId = board.getMemberId();

        noticeService.saveNotice(noticeType, boardId, memberId);
    }

    public String extractTextFromHtml(String boardContent) {
        // HTML을 파싱하여 Document 객체 생성
        Document document = Jsoup.parse(boardContent);

        // 텍스트만 추출
        String text = document.body().text();

        return text; // 추출된 텍스트 반환
    }
}