package app.labs.diary.event;

import app.labs.AiService;
import app.labs.board.model.Board;
import app.labs.diary.dao.DiaryRepository;
import app.labs.diary.model.Diary;
import app.labs.diary.service.DiaryService;
import app.labs.notice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Async
@Transactional(readOnly = true)
@Component
public class DiaryEventListener {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AiService aiService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private DiaryRepository diaryRepository;

    public DiaryEventListener(DiaryService diaryService) { this.diaryService = diaryService; }

    @EventListener
    public void handleDiaryCreateEvent(DiaryCreateEvent diaryCreateEvent) {
        Diary diary = diaryCreateEvent.getDiary();
        String content = diary.getDiaryContent();
        String text = extractTextFromHtml(content);
        int diaryId = diary.getDiaryId();

        log.info("Diary created: " + text);

        String feedbackResult = aiService.feedbackService(text);
        log.info("피드백결과: {}", feedbackResult);

        createNotice(diaryId, "DIARY_FEED_"+feedbackResult);
       diaryRepository.updateDiaryEmotion(diaryId, feedbackResult);
    }

    private void createNotice(int diaryId, String  noticeType) {
        Diary diary = diaryService.getDiaryInfo(diaryId);
        String memberId = diary.getMemberId();

        noticeService.saveNotice(noticeType, diaryId, memberId);
    }

    public String extractTextFromHtml(String boardContent) {
        // HTML을 파싱하여 Document 객체 생성
        Document document = Jsoup.parse(boardContent);

        // 텍스트만 추출
        String text = document.body().text();

        return text; // 추출된 텍스트 반환
    }

}
