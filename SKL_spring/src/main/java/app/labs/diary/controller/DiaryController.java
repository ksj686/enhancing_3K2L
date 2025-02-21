package app.labs.diary.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.labs.attach.model.Attach;
import app.labs.attach.service.AttachService;
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
	
	@Autowired
	AttachService attachService;
		
	@GetMapping(value={"/diary", "/diary/"})
	public String home(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberid");

	    // 🔹 로그인 상태 확인 (세션에 memberid가 없는 경우 로그인 페이지로 리다이렉트)
	    if (memberId == null) {
	        return "redirect:/login"; // 로그인 페이지로 이동
	    }
	    
		model.addAttribute("serverTime", "서버시간");
		
		return "thymeleaf/diary/home";
	}
	
	@GetMapping("/diary/list")
	public String getAllDiary(Model model, HttpSession session) {
		String memberId = (String) session.getAttribute("memberid");

	    // 🔹 로그인 상태 확인 (세션에 memberid가 없는 경우 로그인 페이지로 리다이렉트)
	    if (memberId == null) {
	        return "redirect:/login"; // 로그인 페이지로 이동
	    }
		
		List<Diary> diaryList = diaryService.getDiaryList(memberId);
    	
		model.addAttribute("diaryList", diaryList);
//		log.info("memberId" + memberId);
		return "thymeleaf/diary/list";
	}
	
	@GetMapping("/diary/{diaryId}")
	public String getDiaryInfo(@PathVariable("diaryId") int diaryId, Model model) {
		
		Diary diary = diaryService.getDiaryInfo(diaryId);
		model.addAttribute("diary", diary);
		
		Attach attach = attachService.getAttachFile(diaryId);
		model.addAttribute("attach", attach);
		
		log.info("diary id: " + diaryId + " attach info: " + attach );
				
	    return "thymeleaf/diary/view";
	}

	
	@GetMapping("/diary/insert")
	public String insertDiary(Model model, RedirectAttributes redirectAttributes) {
    	
		Diary diary = new Diary();
		
		diary.setDiaryDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	    model.addAttribute("diary", diary);
		
		return "thymeleaf/diary/diaryform";
	}
	
	@PostMapping("/diary/insert")
	public String insertDiary(@ModelAttribute Diary diary, HttpServletRequest request,
	                          @RequestParam(value= "file", required = false) MultipartFile file,
	                          RedirectAttributes redirectAttributes) {
	    HttpSession session = request.getSession();
	    String memberId = (String) session.getAttribute("memberid");

	    try {
	        diary.setMemberId(memberId);
	        
	        int diaryId = diaryService.createDiaryId();
	        diary.setDiaryId(diaryId);
	        
	        diaryService.insertDiary(diary);
	        
	       if(file != null && !file.isEmpty()) {
	    	   log.info("파일명: " + file.getOriginalFilename());
	    	   
	    	   Attach attach = new Attach();
	    	   attach.setDiaryId(diaryId);
	    	   
	    	   String attachName = file.getOriginalFilename();
	    	   attach.setAttachName(attachName);
	    	   
	    	   long attachSize = file.getSize();
	    	   attach.setAttachSize(attachSize);
	    	   
	    	   String attachDir = "C:/labs_python/SamkimILee/SKL_spring/src/main/resources/static/attach/" + attachName;
	    	   file.transferTo(new File(attachDir));
	    	   attach.setAttachUrl(attachDir);
	    	   
	    	   attachService.insertAttach(attach);
	    	   log.info("attach 등록 성공!");
	       }
	        
	        redirectAttributes.addFlashAttribute("message", "일기가 등록되었습니다.");
	        log.info("일기 등록 성공");
	        
	    } catch (Exception ex) {
	    		ex.printStackTrace();
	    		log.error("일기 등록 오류: ", ex.getMessage());
	    }
	    return "redirect:/diary/list";
	}

	@GetMapping("/diary/update")
	public String updateDiary(@RequestParam("diaryId") int diaryId, Model model) {
	    Diary diary = diaryService.getDiaryInfo(diaryId);
	   
	    if (diary != null) {
	         diary.setDiaryDate(diary.getDiaryDate().substring(0, 10));
	         model.addAttribute("diary", diary);
	         model.addAttribute("update", true);
	         
	         Attach attach = attachService.getAttachFile(diaryId);
	         
	         if(attach !=null) {
	        	 model.addAttribute("attach", attach); // 🔥 첨부파일 모델 추가
	         	}
	    	}

	    return "thymeleaf/diary/diaryform";
	}
	
	@PostMapping("/diary/update")
	public String updateDiary(@ModelAttribute Diary diary, RedirectAttributes redirectAttributes, 
		                      HttpServletRequest request, @RequestParam(value= "file", required = false) MultipartFile file) {  
		try {
		    log.info("수정 요청된 Diary ID : " + diary.getDiaryId()); // ✅ 수정 요청된 Diary 로그 확인
		    diaryService.updateDiary(diary);
		   
		    
		    // 기존 첨부파일 확인
		    Attach existingAttach = attachService.getAttachFile(diary.getDiaryId());
		    
		    if(file != null && !file.isEmpty()) { // 새로운 첨부파일이 있는지 확인
			   // 새로운 첨부파일이 있는 경우

			   if(existingAttach != null) {
				   // 기존 첨부파일 삭제
				   int oldAttachId = existingAttach.getAttachId();
				   attachService.deleteAttach(oldAttachId);
				   log.info("기존 첨부파일 삭제 완료");

				   // 새로운 첨부파일
				   Attach attach = new Attach();
				   attach.setDiaryId(diary.getDiaryId());

				   String attachName = file.getOriginalFilename();
				   attach.setAttachName(attachName);

				   String attachDir = "C:/labs_python/SamkimILee/SKL_spring/src/main/resources/static/attach/" + attachName;
				   file.transferTo(new File(attachDir));
				   attach.setAttachUrl(attachDir);

				   long attachSize = file.getSize();
				   attach.setAttachSize(attachSize);

				   attachService.insertAttach(attach);


			   } else {

				   log.info("기존 첨부파일 없음! insertAttach ");

				   Attach attach = new Attach();
				   attach.setDiaryId(diary.getDiaryId());

				   String attachName = file.getOriginalFilename();
				   attach.setAttachName(attachName);

				   String attachDir = "C:/labs_python/SamkimILee/SKL_spring/src/main/resources/static/attach/" + attachName;
				   file.transferTo(new File(attachDir));
				   attach.setAttachUrl(attachDir);

				   long attachSize = file.getSize();
				   attach.setAttachSize(attachSize);

				   attachService.insertAttach(attach);
			   }
		    	   
		    }

		    redirectAttributes.addFlashAttribute("message", "일기가 수정되었습니다.");
		    log.info("일기 수정 성공");

		} catch (Exception ex) {
		    ex.printStackTrace();
		    log.error("일기 수정 오류: ", ex.getMessage());
		}
		return "redirect:/diary/list";
	}


	
	@GetMapping("diary/delete")
	public String deleteDiary(@RequestParam("diaryId") int diaryId, HttpServletRequest request,
							  RedirectAttributes redirectAttributes) {
		
		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("memberid");
	
		Diary diary = diaryService.getDiaryInfo(diaryId);
		String memberId = diary.getMemberId();
		
		if(sessionId.equals(memberId)) {
			diaryService.deleteDiary(diaryId);
			attachService.deleteAttachByDiary(diaryId);
		}
		
		redirectAttributes.addFlashAttribute("message", "일기가 삭제되었습니다.");
		
		return "redirect:/diary/list";
	}


	
}
