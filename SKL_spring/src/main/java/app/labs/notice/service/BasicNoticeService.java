package app.labs.notice.service;

import app.labs.notice.dao.NoticeRepository;
import app.labs.notice.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return noticeRepository.getNoticeList(memberId);
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
    public void readNotice(int noticeId) {
        noticeRepository.readNotice(noticeId);
    }

}
