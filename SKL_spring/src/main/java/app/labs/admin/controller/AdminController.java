package app.labs.admin.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.labs.admin.service.AdminService;

import app.labs.register.model.Member;

@Controller
public class AdminController {

	@Autowired
    private AdminService adminService;
	
	// 회원목록
    @GetMapping("/admin/getMemberList")
    public List<Member> getMemberList(@RequestParam(name = "memberId") String memberId
    		, @RequestParam(name = "memberName") String memberName
    		, Model model) {
    	List<Member> members = new ArrayList<>();
//    	memberId 또는 memberName이 있으면 해당 맴버만 조회, 없으면 전체 회원 조회
    	model.addAttribute("memberList", members);
        return members;
    }
    
    
    @GetMapping("/admin/member-status/year")
    public String memberStatusByYear(Model model) {
    	model.addAttribute("memberStatus", adminService.getMemberStatusByYear());
    	
        return "/admin/member-status/year";
    }
    
    @GetMapping("/admin/member-status/month")
    public String memberStatusByMonth(Model model) {
    	model.addAttribute("memberStatus", adminService.getMemberStatusByMonth());
    	
        return "/admin/member-status/month";
    }
    
    @GetMapping("/admin/member-status/day")
    public String memberStatusByDay(Model model) {
    	model.addAttribute("memberStatus", adminService.getMemberStatusByDay());
    	
        return "/admin/member-status/day";
    }
    
    // 미션 달성 현황
    // return 형태 변경될 수 있음
    @GetMapping("/admin/mission-status")
    public String missionStatus(Model model) {
//    	조회한 데이터 전달
    	model.addAttribute("", "");
    	
    	//??
        return "/admin/mission-status";
    }
    
    // 사용자 감정통계. diary 테이블에서 전체 emotion 날짜 단위로 가져오기?
    // 감정 게시판 통계는 추후?
    @GetMapping("/admin/emotion-stat")	//statistics
    public String emotionStat(Model model) {
//    	조회한 데이터 전달
    	model.addAttribute("", "");
    	
    	//??
        return "/admin/emotion-stat";
    }
    
    // 게시글 관리
    @GetMapping("/admin/manage-board")	//statistics
    public String manageBoard(Model model) {
//    	조회한 데이터 전달
    	model.addAttribute("", "");
    	
    	//??
        return "/admin/manage-board";
    }
    
}
