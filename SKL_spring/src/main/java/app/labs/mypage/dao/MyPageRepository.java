package app.labs.mypage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyPageRepository {
    List<Map<String, Object>> getBoardStats(@Param("memberId") String memberId, @Param("date") String date);
    List<Map<String, Object>> getJournalStats(@Param("memberId") String memberId, @Param("date") String date);
}
