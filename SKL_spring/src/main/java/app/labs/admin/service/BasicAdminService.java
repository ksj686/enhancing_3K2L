package app.labs.admin.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.labs.admin.dao.AdminRepository;
import app.labs.register.model.Member;

@Service
public class BasicAdminService implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<Member> getMemberList(String memberId, String memberName) {
        return adminRepository.getMemberList(memberId, memberName);
    }

    @Override
    public Map<String, Object> getMemberStatusByYear() {
        Map<String, Object> result = new HashMap<>();
        result.put("yearlyStats", adminRepository.getMemberStatsByYear());
        return result;
    }

    @Override
    public Map<String, Object> getMemberStatusByMonth() {
        Map<String, Object> result = new HashMap<>();
        LocalDate now = LocalDate.now();
        result.put("monthlyStats", adminRepository.getMemberStatsByMonth(now.getYear()));
        return result;
    }

    @Override
    public Map<String, Object> getMemberStatusByDay() {
        Map<String, Object> result = new HashMap<>();
        LocalDate now = LocalDate.now();
        String yearMonth = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        result.put("dailyStats", adminRepository.getMemberStatsByDay(yearMonth));
        return result;
    }

    @Override
    public Map<String, Object> getMissionStatus() {
        Map<String, Object> result = new HashMap<>();
        result.put("completionStats", adminRepository.getMissionCompletionStats());
        result.put("participationStats", adminRepository.getMissionParticipationStats());
        return result;
    }

    @Override
    public Map<String, Object> getEmotionStats() {
        Map<String, Object> result = new HashMap<>();
        result.put("emotionStats", adminRepository.getEmotionStats());
        return result;
    }

    @Override
    public Map<String, Object> getEmotionTrends(String startDate, String endDate) {
        Map<String, Object> result = new HashMap<>();
        result.put("emotionTrends", adminRepository.getEmotionTrendsByPeriod(startDate, endDate));
        return result;
    }

    @Override
    public Map<String, Object> getBoardManagementData() {
        Map<String, Object> result = new HashMap<>();
        result.put("boardList", adminRepository.getBoardList());
        result.put("reportedPosts", adminRepository.getReportedPosts());
        return result;
    }

    @Override
    public boolean updateBoardStatus(Long boardId, String status) {
        return adminRepository.updateBoardStatus(boardId, status) > 0;
    }

    @Override
    @Transactional
    public void updateMemberStatusList(List<String> memberIdList, List<String> memberStatusList) {
        if (memberIdList.size() != memberStatusList.size()) {
            throw new IllegalArgumentException("Member ID list and status list must have the same size");
        }
        
        for (int i = 0; i < memberIdList.size(); i++) {
            adminRepository.updateMemberStatus(memberIdList.get(i), memberStatusList.get(i));
        }
    }
}
