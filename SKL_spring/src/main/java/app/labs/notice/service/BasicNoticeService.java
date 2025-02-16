package app.labs.notice.service;

import app.labs.notice.dao.NoticeRepository;
import app.labs.notice.model.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class BasicNoticeService implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public void saveNotice(String noticeType, int boardId, String memberId) {
        noticeRepository.saveNotice(noticeType, boardId, memberId);
    }

    @Override
    public List<Notice> getNoticeList(String memberId) {
        List<Notice> noticeList = noticeRepository.getNoticeList(memberId);

        // 날짜 변환
        for (Notice notice : noticeList) {
            String noticeDate = notice.getNoticeDate(); // 원래 날짜 문자열
            log.info("날짜: {}", noticeDate);
        }

        return noticeList;
    }

    @Override
    public int countNotice(String memberId) {
        return noticeRepository.countNotice(memberId);
    }

    @Override
    public void sendNotice(int noticeId) {
        noticeRepository.sendNotice(noticeId);
    }

    @Override
    public void deleteNotice(int noticeId) {
        noticeRepository.deleteNotice(noticeId);
    }

    @Override
    public void readNotice(String memeberId) {
        noticeRepository.readNotice(memeberId);
    }
}
