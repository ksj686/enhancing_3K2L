package app.labs.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.register.model.Member;

@Mapper
public interface AdminRepository {
    // 회원 목록 조회 관련
    List<Member> getMemberList(@Param("memberId") String memberId, @Param("memberName") String memberName);
    // List<Member> findByMemberId(String memberId);
    // List<Member> findByMemberName(String memberName);

    // 회원 상태 통계 관련
    List<Map<String, Object>> getMemberStatsByYear();
    List<Map<String, Object>> getMemberStatsByMonth(@Param("year") int year);
    List<Map<String, Object>> getMemberStatsByDay(@Param("yearMonth") String yearMonth);

    // 회원 상태 수정
    int updateMemberStatus(@Param("memberId") String memberId, @Param("memberStatus") String memberStatus);

    // 미션 현황 관련
    List<Map<String, Object>> getMissionCompletionStats();
    List<Map<String, Object>> getMissionParticipationStats();
    
    // 감정 통계 관련
    List<Map<String, Object>> getEmotionStats();
    List<Map<String, Object>> getEmotionTrendsByPeriod(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
    // 게시글 관리 관련
    List<Map<String, Object>> getBoardList();
    List<Map<String, Object>> getReportedPosts();
    int updateBoardStatus(@Param("boardId") Long boardId, @Param("status") String status);
}
