package app.labs.diary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import app.labs.diary.service.DiaryService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DiaryController {
	@Autowired
	DiaryService diaryService;
	
	@GetMapping("/diary/test")
	public String home(Model model) {
		
		model.addAttribute("serverTime", "서버시간");
		
		return "thymeleaf/diary/home";
	}
	
}
