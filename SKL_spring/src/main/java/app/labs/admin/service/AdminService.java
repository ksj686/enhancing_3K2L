package app.labs.admin.service;

import java.util.List;
import java.util.Map;

import app.labs.register.model.Member;
import app.labs.board.model.Board;

public interface AdminService {
    // 회원 목록 조회
    List<Member> getMemberList(String memberId, String memberName);
    
    // 회원 통계
    Map<String, Object> getMemberStatsByYear();
    Map<String, Object> getMemberStatsByMonth();
    Map<String, Object> getMemberStatsByDay();

    // 회원 상태 일괄 수정
    void updateMemberStatusList(List<String> memberIdList, List<String> memberStatusList);
    
    // 미션 현황
    Map<String, Object> getMissionStatus();
    
    // 감정 통계
    Map<String, Object> getEmotionStats();
    Map<String, Object> getEmotionTrends(String startDate, String endDate);
    

    
    // Board 관련 메서드
    List<Board> getBoardList();
    
    // 게시글 상세 조회
    Board getBoardDetail(int boardId);

    // 게시글 상태 일괄 수정
    int updateBoardList(List<String> boardIdList, List<String> boardOffensiveList, List<Integer> boardReportList);
}
