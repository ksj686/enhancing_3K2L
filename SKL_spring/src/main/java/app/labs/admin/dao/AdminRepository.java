package app.labs.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.register.model.Member;
import app.labs.board.model.Board;

@Mapper
public interface AdminRepository {
    // 회원 목록 조회 관련
    List<Member> getMemberList(@Param("memberId") String memberId, @Param("memberName") String memberName);

    // 회원 통계 관련
    List<Map<String, Object>> getMemberStatsByYear();
    List<Map<String, Object>> getMemberStatsByMonth();
    List<Map<String, Object>> getMemberStatsByDay();

    // 회원 상태 수정
    int updateMemberStatus(@Param("memberId") String memberId, @Param("memberStatus") String memberStatus);

    // 미션 현황 관련
    List<Map<String, Object>> getMissionCompletionStats();
    List<Map<String, Object>> getMissionParticipationStats();
    
    // 감정 통계 관련
    List<Map<String, Object>> getEmotionStats();
    List<Map<String, Object>> getEmotionTrendsByPeriod(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
    // Board 관련 메서드
    List<Board> getBoardList();
    void updateBoardStatus(Map<String, Object> paramMap);
    
    // 게시글 상세 조회
    Board getBoardDetail(int boardId);
}
