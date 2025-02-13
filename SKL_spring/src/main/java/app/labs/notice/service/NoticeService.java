package app.labs.notice.service;

import app.labs.notice.model.Notice;

import java.util.List;

public interface NoticeService {
    void saveNotice(String noticeType, int BoardId, String memberId);
    List<Notice> getNoticeList(String memberId);
    int countNotice(String memberId);
    void sendNotice(int noticeId);
    void readNotice(int noticeId);
}
