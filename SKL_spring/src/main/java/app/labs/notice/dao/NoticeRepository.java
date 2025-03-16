package app.labs.notice.dao;


import app.labs.notice.model.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeRepository {
    void saveNotice(@Param("noticeType") String noticeType, @Param("boardId") int boardId, @Param("memberId") String memberId);
    List<Notice> getNoticeList(String memberId);
    int countNotice(String memberId);
    void sendNotice(int noticeId);
    void deleteNotice(int noticeId);
    void readNotice(String memeberId);
}
