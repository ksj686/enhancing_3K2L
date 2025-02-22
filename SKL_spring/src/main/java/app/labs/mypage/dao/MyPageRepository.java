package app.labs.mypage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.diary.model.Diary;

@Mapper
public interface MyPageRepository {
    List<Map<String, Object>> getBoardStats(@Param("memberId") String memberId, @Param("date") String date);
    List<Map<String, Object>> getJournalStats(@Param("memberId") String memberId, @Param("date") String date);
    List<Diary> getMyPageJournal(@Param("memberId") String memberId, @Param("category") String category, @Param("date") String date);
}
