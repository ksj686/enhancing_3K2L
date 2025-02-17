package app.labs.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import app.labs.admin.service.AdminService;
import app.labs.register.model.Member;
import lombok.extern.slf4j.Slf4j;
import app.labs.board.model.Board;
import app.labs.admin.model.Events;

@Slf4j
@Controller
// @RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 관리자 메인 페이지
    @GetMapping("/admin")
    public String adminPage() {
        return "thymeleaf/admin/admin_home";
    }

    // 관리자 로그인
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "thymeleaf/admin/loginform";
    }

    // 로그인 처리 메서드
    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam("id") String id, @RequestParam("pwd") String pwd, HttpSession session,
            RedirectAttributes redirectAttrs) {
        Map<String, Object> admin = adminService.findById(id);
        if (admin != null) {
            if (admin.get("ADMIN_PWD").equals(pwd)) {
                session.setMaxInactiveInterval(600); // 10분
                session.setAttribute("adminId", id);
                return "redirect:/admin";
            } else {
                session.invalidate();
                redirectAttrs.addFlashAttribute("message", "아이디 또는 패스워드가 잘못되었습니다.");
                return "redirect:/admin/login";
            }
        } else {
            session.invalidate();
            redirectAttrs.addFlashAttribute("message", "아이디 또는 패스워드가 잘못되었습니다.");
            return "redirect:/admin/login";
        }
    }

    // 로그아웃 메서드
    @GetMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    // 회원목록 - 페이지 전체 반환
    @GetMapping("/admin/member-list")
    public String memberListFullPage(Model model) {
        return "thymeleaf/admin/member_list";
    }

    // 회원목록 - 부분 페이지 갱신
    @PostMapping("/admin/getMemberList")
    public String getMemberList(
            @RequestParam(name = "memberId", required = false) String memberId,
            @RequestParam(name = "memberName", required = false) String memberName,
            Model model) {
        List<Member> members = adminService.getMemberList(memberId, memberName);
        model.addAttribute("memberList", members);
        return "thymeleaf/admin/member_list :: memberListFragment"; // Thymeleaf fragment 반환
    }

    // 회원목록 - JSON 데이터 반환
    // @PostMapping("/admin/getMemberList")
    // @ResponseBody
    // public List<Member> getMemberList(@RequestParam(name = "memberId", required =
    // false) String memberId
    // , @RequestParam(name = "memberName", required = false) String memberName) {
    // return adminService.getMemberList(memberId, memberName);
    // }
    @GetMapping("/admin/member-stats")
    public String memberStats(Model model) {
        return "thymeleaf/admin/member_stats";
    }

    // // 회원 통계 - 연도별
    // @GetMapping("/admin/member-stats/year")
    // @ResponseBody
    // public List<Map<String, Object>> memberStatByYear() {
    // return (List<Map<String, Object>>)
    // adminService.getMemberStatsByYear().get("yearlyStats");
    // }

    // // 회원 통계 - 월별
    // @GetMapping("/admin/member-stats/month")
    // @ResponseBody
    // public List<Map<String, Object>> memberStatByMonth() {
    // return (List<Map<String, Object>>)
    // adminService.getMemberStatsByMonth().get("monthlyStats");
    // }

    // // 회원 통계 - 일별
    // @GetMapping("/admin/member-stats/day")
    // @ResponseBody
    // public List<Map<String, Object>> memberStatByDay() {
    // return (List<Map<String, Object>>)
    // adminService.getMemberStatsByDay().get("dailyStats");
    // }

    // 회원 상태 일괄 수정
    @PostMapping("/admin/updateMemberStatusList")
    @ResponseBody
    public Map<String, Boolean> updateMemberStatusList(@RequestParam("memberIdList[]") List<String> memberIdList,
            @RequestParam("memberStatusList[]") List<String> memberStatusList) {
        Map<String, Boolean> response = new HashMap<>();
        try {
            adminService.updateMemberStatusList(memberIdList, memberStatusList);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
        }
        return response;
    }

    // 미션 달성 현황
    // return 형태 변경될 수 있음
    @GetMapping("/admin/mission-status")
    public String missionStatus(Model model) {
        // 조회한 데이터 전달
        model.addAttribute("", "");

        // ??
        return "thymeleaf/admin/mission-status";
    }

    // 게시글 관리 페이지
    @GetMapping("/admin/board-list") // statistics
    public String manageBoard(Model model) {
        return "thymeleaf/admin/board_list";
    }

    // 게시글 조회
    @PostMapping("/admin/getBoardList") // statistics
    public String getBoardList(Model model) {
        List<Board> boards = adminService.getBoardList();
        model.addAttribute("boardList", boards);
        return "thymeleaf/admin/board_list :: boardListFragment";
    }

    // 게시글 상세 정보 조회
    @GetMapping("/admin/getBoardDetail")
    @ResponseBody
    public Board getBoardDetail(@RequestParam("boardId") int boardId) {
        return adminService.getBoardDetail(boardId);
    }

    // 게시글 상태 일괄 수정
    @PostMapping("/admin/updateBoardList")
    @ResponseBody
    public Map<String, Boolean> updateBoardList(@RequestBody Map<String, Object> requestData) {
        Map<String, Boolean> response = new HashMap<>();
        try {
            @SuppressWarnings("unchecked")
            List<String> boardIdList = (List<String>) requestData.get("boardIdList");
            @SuppressWarnings("unchecked")
            List<String> boardOffensiveList = (List<String>) requestData.get("boardOffensiveList");
            @SuppressWarnings("unchecked")
            List<Integer> boardReportList = (List<Integer>) requestData.get("boardReportList");

            int result = adminService.updateBoardList(boardIdList, boardOffensiveList, boardReportList);
            response.put("success", true);
            log.info(result + "개 변경사항");
        } catch (Exception e) {
            response.put("success", false);
            log.error("Error updating board status: ", e);
        }
        return response;
    }

    // 사용자 감정통계. diary 테이블에서 전체 emotion 날짜 단위로 가져오기?
    // 감정 게시판 통계는 추후?
    @GetMapping("/admin/emotion-stats") // statistics
    public String emotionStat(Model model) {
        return "thymeleaf/admin/emotion_stats";
    }

    // 당일 감정 통계
    @GetMapping("/admin/emotion-stats/diary/today")
    @ResponseBody
    public List<Map<String, Object>> getDailyEmoDiary() {
        return adminService.getDailyEmoDiary();
    }

    @GetMapping("/admin/emotion-stats/diary/total")
    @ResponseBody
    public List<Map<String, Object>> getTotalEmoDiary() {
        return adminService.getTotalEmoDiary();
    }

    @GetMapping("/admin/emotion-stats/board/today")
    @ResponseBody
    public List<Map<String, Object>> getDailyEmoBoard() {
        return adminService.getDailyEmoBoard();
    }

    @GetMapping("/admin/emotion-stats/board/total")
    @ResponseBody
    public List<Map<String, Object>> getTotalEmoBoard() {
        return adminService.getTotalEmoBoard();
    }

    @GetMapping("/admin/event-storyboard")
    public String eventStoryboard(Model model) {
        return "thymeleaf/admin/event_storyboard";
    }

    @PostMapping("/admin/getEvents")
    public String getEvents(Model model) {
        List<Events> events = adminService.getEvents();
        // log.info(events.toString());
        model.addAttribute("events", events);
        return "thymeleaf/admin/event_storyboard :: eventListFragment";
    }

    @PostMapping("/admin/insertEvent")
    @ResponseBody
    public Map<String, Object> insertEvent(@RequestBody Events event) {
        Map<String, Object> response = new HashMap<>();
        try {
            adminService.insertEvent(event);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    @PostMapping("/admin/updateEvent")
    @ResponseBody
    public Map<String, Object> updateEvent(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();
        try {
            // log.info(requestData.toString());
            adminService.updateEvent(requestData);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    @PostMapping("/admin/deleteEvent")
    @ResponseBody
    public Map<String, Object> deleteEvent(@RequestParam("eventId") int eventId) {
        Map<String, Object> response = new HashMap<>();
        try {
            adminService.deleteEvent(eventId);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    @GetMapping("/admin/event-stats")
    public String eventStats(Model model) {
        List<Events> eventList = adminService.getEvents();
        model.addAttribute("eventList", eventList);
        return "thymeleaf/admin/event_stats";
    }

    @GetMapping("/admin/event-stats/past")
    @ResponseBody
    public List<Map<String, Object>> getPastEventStats(@RequestParam("eventId") int eventId,
            @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
            @RequestParam("periodUnit") String periodUnit) {

        List<Map<String, Object>> pastEventStats = adminService.getPastEventStats(eventId, startDate, endDate,
                periodUnit);

        return pastEventStats;
    }

    @GetMapping("/admin/event-stats/today")
    @ResponseBody
    public List<Map<String, Object>> getTodayEventStats(@RequestParam("eventId") int eventId) {
        return adminService.getTodayEventStats(eventId);
    }

    @GetMapping("/admin/event-stats/total")
    @ResponseBody
    public List<Map<String, Object>> getTotalEventStats() {
        return adminService.getTotalEventStats();
    }

    @GetMapping("/admin/member-stats/sign-up")
    @ResponseBody
    public List<Map<String, Object>> getSignUpStats(@RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("periodUnit") String periodUnit) {

        List<Map<String, Object>> signUpStats = adminService.getSignUpStats(startDate, endDate, periodUnit);

        return signUpStats;
    }

    @GetMapping("/admin/member-stats/drop-out")
    @ResponseBody
    public List<Map<String, Object>> getDropOutStats(@RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("periodUnit") String periodUnit) {

        List<Map<String, Object>> dropOutStats = adminService.getDropOutStats(startDate, endDate, periodUnit);

        return dropOutStats;
    }

    @GetMapping("/admin/visitor-today")
    @ResponseBody
    public Map<String, Integer> getVisitorCount() {
        int count = adminService.getVisitorCount();
        return Collections.singletonMap("count", count);
    }

    @GetMapping("/admin/today-events-stats")
    @ResponseBody
    public List<Map<String, Object>> getTodayEventsStats() {
        return adminService.getTodayEventsStats();
    }
}
