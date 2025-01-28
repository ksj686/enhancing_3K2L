package app.labs.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.labs.admin.service.AdminService;
import app.labs.register.model.Member;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    // 홈페이지
    @GetMapping("/admin")
    public String adminPage() {
        return "thymeleaf/admin/admin";
    }

    // 회원목록 - 페이지 전체 반환
    @GetMapping("/admin/members")
    public String memberListPage(Model model) {
        return "thymeleaf/admin/admin";
    }

    // 회원목록 - 부분 페이지 갱신
    @PostMapping("/admin/getMemberList")
    public String getMemberList(@RequestParam(name = "memberId", required = false) String memberId
            , @RequestParam(name = "memberName", required = false) String memberName
    		, Model model) {
        List<Member> members = adminService.getMemberList(memberId, memberName);
    	model.addAttribute("memberList", members);
        return "thymeleaf/admin/admin :: memberListFragment";  // Thymeleaf fragment 반환
    }
    // 회원목록 - JSON 데이터 반환
    // @PostMapping("/admin/getMemberList")
    // @ResponseBody
    // public List<Member> getMemberList(@RequestParam(name = "memberId", required = false) String memberId
    //         , @RequestParam(name = "memberName", required = false) String memberName) {
    //     return adminService.getMemberList(memberId, memberName);
    // }
    
    // 회원 상태 - 연도별
    @GetMapping("/admin/member-status/year")
    public String memberStatusByYear(Model model) {
        model.addAttribute("memberStatus", adminService.getMemberStatusByYear());
        return "thymeleaf/admin/admin :: yearlyStatsFragment";
    }
    
    // 회원 상태 - 월별
    @GetMapping("/admin/member-status/month")
    public String memberStatusByMonth(Model model) {
        model.addAttribute("memberStatus", adminService.getMemberStatusByMonth());
        return "thymeleaf/admin/admin :: monthlyStatsFragment";
    }
    
    // 회원 상태 - 일별
    @GetMapping("/admin/member-status/day")
    public String memberStatusByDay(Model model) {
        model.addAttribute("memberStatus", adminService.getMemberStatusByDay());
        return "thymeleaf/admin/admin :: dailyStatsFragment";
    }
    
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
//    	조회한 데이터 전달
    	model.addAttribute("", "");
    	
    	//??
        return "thymeleaf/admin/mission-status";
    }
    
    // 사용자 감정통계. diary 테이블에서 전체 emotion 날짜 단위로 가져오기?
    // 감정 게시판 통계는 추후?
    @GetMapping("/admin/emotion-stat")	//statistics
    public String emotionStat(Model model) {
//    	조회한 데이터 전달
    	model.addAttribute("", "");
    	
    	//??
        return "thymeleaf/admin/emotion-stat";
    }
    
    // 게시글 관리
    @GetMapping("/admin/manage-board")	//statistics
    public String manageBoard(Model model) {
//    	조회한 데이터 전달
    	model.addAttribute("", "");
    	
    	//??
        return "thymeleaf/admin/manage-board";
    }
    
}
