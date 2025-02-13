package app.labs.admin.service;

import java.util.List;
import java.util.Map;

import app.labs.register.model.Member;
import app.labs.board.model.Board;
import app.labs.admin.model.Events;

public interface AdminService {

    // 관리자 로그인
    Map<String, Object> findById(String id);

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

    List<Map<String, Object>> getDailyEmoDiary();

    List<Map<String, Object>> getTotalEmoDiary();

    List<Map<String, Object>> getDailyEmoBoard();

    List<Map<String, Object>> getTotalEmoBoard();

    // Board 관련 메서드
    List<Board> getBoardList();

    // 게시글 상세 조회
    Board getBoardDetail(int boardId);

    // 게시글 상태 일괄 수정
    int updateBoardList(List<String> boardIdList, List<String> boardOffensiveList, List<Integer> boardReportList);

    List<Events> getEvents();

    void updateEvent(Map<String, Object> requestData);

    void insertEvent(Events event);

    void deleteEvent(int eventId);

    List<Map<String, Object>> getPastEventStats(int eventId, String startDate, String endDate, String periodUnit);

    List<Map<String, Object>> getTodayEventStats(int eventId);

    List<Map<String, Object>> getTotalEventStats();
}
