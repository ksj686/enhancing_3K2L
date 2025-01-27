package app.labs.diary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    	model.addAttribute("memberId", memberId);
		model.addAttribute("serverTime", "서버시간");
		log.info("memberId:", memberId);
		
		return "thymeleaf/diary/home";
	}
	
	@GetMapping("/diary/list/{memberId}")
	public String getAllDiary(@PathVariable("memberId") String memberId, Model model, HttpSession session) {
    	memberId = (String) session.getAttribute("memberId");
    	List<Diary> diaryList = diaryService.getDiaryList(memberId);
    	
		model.addAttribute("diaryList", diaryList);
		
		return "thymeleaf/diary/list";
	}
	
}
