package app.labs.admin.service;

import java.util.List;
import java.util.Map;

import app.labs.register.model.Member;

public interface AdminService {
    // 회원 목록 조회
    List<Member> getMemberList(String memberId, String memberName);
    
    // 회원 상태 통계
    Map<String, Object> getMemberStatusByYear();
    Map<String, Object> getMemberStatusByMonth();
    Map<String, Object> getMemberStatusByDay();
    
    // 미션 현황
    Map<String, Object> getMissionStatus();
    
    // 감정 통계
    Map<String, Object> getEmotionStats();
    Map<String, Object> getEmotionTrends(String startDate, String endDate);
    
    // 회원 상태 일괄 수정
    void updateMemberStatusList(List<String> memberIdList, List<String> memberStatusList);
    
    // 게시글 관리
    Map<String, Object> getBoardManagementData();
    boolean updateBoardStatus(Long boardId, String status);
}
