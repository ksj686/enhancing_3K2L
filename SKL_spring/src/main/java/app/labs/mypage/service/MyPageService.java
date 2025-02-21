package app.labs.mypage.service;

import java.util.List;
import java.util.Map;

public interface MyPageService {
    List<Map<String, Object>> getBoardStats(String memberId, String date);
    List<Map<String, Object>> getJournalStats(String memberId, String date);
}
