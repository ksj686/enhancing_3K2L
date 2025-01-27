package app.labs.diary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.labs.diary.model.Diary;
import app.labs.diary.service.DiaryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DiaryController {
	@Autowired
	DiaryService diaryService;
	
	@GetMapping("/diary/test")
	public String home(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
    	String memberId = (String) session.getAttribute("memberid");
    	log.info("memberId: " + memberId);
    	model.addAttribute("memberId", memberId);
		model.addAttribute("serverTime", "서버시간");
		
		return "thymeleaf/diary/home";
	}
	
	@GetMapping("/diary/list/{memberId}")
	public String getAllDiary(@PathVariable("memberId") String memberId, Model model, HttpSession session) {
    	memberId = (String) session.getAttribute("memberid");
    	List<Diary> diaryList = diaryService.getDiaryList(memberId);
    	
		model.addAttribute("diaryList", diaryList);
		log.info("memberId" + memberId);
		return "thymeleaf/diary/list";
	}
	
	@GetMapping("/diary/insert")
	public String insertDiary(Model model, HttpSession session) {
    	
		String memberId = (String)session.getAttribute("memberid");
		model.addAttribute("memberId", memberId);
		
		return "thymeleaf/diary/insertform";
	}
	
	@PostMapping("/diary/insert")
	public String insertDiary(Diary diary, RedirectAttributes redirectAttributes, HttpSession session) {
    	String memberId = (String) session.getAttribute("memberid");
		
		try {
			diaryService.insertDiary(diary);
			diary.setMemberId(memberId);
			redirectAttributes.addFlashAttribute("message", "저장 되었습니다.");
		}
		catch(RuntimeException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/diary/list/" + memberId;
	}
}
