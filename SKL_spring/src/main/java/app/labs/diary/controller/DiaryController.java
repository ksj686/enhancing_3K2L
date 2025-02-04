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
	AttachService attachService;
	
	@Autowired
	DiaryService diaryService;
	
	// 파일 저장 경로 지정
	private final String uploadDir = "C:/labs_python/SamkimILee/SKL_spring/src/main/resources/static/attach/";
	
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
		String sessionMemberId = (String) session.getAttribute("memberid");

	    // 🔹 로그인 상태 확인 (세션에 memberid가 없는 경우 로그인 페이지로 리다이렉트)
	    if (sessionMemberId == null) {
	        return "redirect:/login"; // 로그인 페이지로 이동
	    }

	    // 🔹 세션에 저장된 ID와 URL의 memberId가 일치하는지 확인 -> 아직 테스트 못해봄
	    if (!sessionMemberId.equals(memberId)) {
	        return "redirect:/"; // 권한 없으면 메인 페이지로 이동
	    }
		
		List<Diary> diaryList = diaryService.getDiaryList(memberId);
    	
		model.addAttribute("diaryList", diaryList);
//		log.info("memberId" + memberId);
		return "thymeleaf/diary/list";
	}
	
	@GetMapping("/diary/insert")
	public String insertDiary(Model model, HttpSession session) {
    	
		String memberId = (String)session.getAttribute("memberid");
		model.addAttribute("memberId", memberId);
		model.addAttribute("diary", new Diary());
		
		return "thymeleaf/diary/insertform";
	}
	
	@PostMapping("/diary/insert")
	public String insertDiary(@ModelAttribute Diary diary, HttpServletRequest request,
	                          @RequestParam("file") MultipartFile attach,
	                          RedirectAttributes redirectAttributes) {
	    HttpSession session = request.getSession();
	    String memberId = (String) session.getAttribute("memberid");

	    try {
	        diary.setMemberId(memberId);
	        diaryService.insertDiary(diary);

	        int diaryId = diary.getDiaryId(); // ✅ 자동 증가된 diaryId 가져오기
	        log.info("새로 생성된 diaryId: " + diaryId); // 🔥 로그 추가

	        if (attach != null && !attach.isEmpty()) {  // ✅ attach 사용
	            log.info("업로드된 파일명: " + attach.getOriginalFilename());
	            String filePath = uploadDir + attach.getOriginalFilename();
	            attach.transferTo(new File(filePath));

	            Attach newAttach = new Attach();
	            newAttach.setDiaryId(diaryId);
	            newAttach.setAttachName(attach.getOriginalFilename());  // ✅ attach에서 데이터 가져옴
	            newAttach.setAttachSize(String.valueOf(attach.getSize()));
	            newAttach.setAttachUrl("/static/attach/" + attach.getOriginalFilename());
	            
	            log.info("저장될 Attach 정보: " + attach); // 🔥 로그 추가
	            attachService.insertAttach(newAttach);
	            }
	        redirectAttributes.addFlashAttribute("message", "일기가 등록되었습니다.");
	        log.info("일기 등록 성공");
	    } catch (Exception ex) {
	    		ex.printStackTrace();
	    		log.error("일기 등록 오류: ", ex.getMessage());
	    }
	    return "redirect:/diary/list/" + memberId;
	}

	
	@GetMapping("/diary/{diaryId}")
	public String getDiaryInfo(@PathVariable("diaryId") int diaryId, Model model) {
		// 🔹 일기 정보 가져오기
	    Diary diary = diaryService.getDiaryInfo(diaryId);
	    model.addAttribute("diary", diary);

	    // 🔹 첨부파일 가져오기
	    Attach attach = attachService.getAttachFile(diaryId);
	    model.addAttribute("attach", attach);

	    // 🔹 로그 출력 (디버깅용)
	    log.info("조회된 Diary ID: " + diaryId);
	    log.info("Diary 정보: " + diary);
	    log.info("Attach 정보: " + attach);

	    return "thymeleaf/diary/view";
	}

	
	@GetMapping("/diary/update")
	public String updateDiary(@RequestParam("diaryId") int diaryId, Model model) {   	
		model.addAttribute("diary", diaryService.getDiaryInfo(diaryId));
		return "thymeleaf/diary/updateform";
	}
	
	@PostMapping("/diary/update")
	public String updateDiary(Diary diary, RedirectAttributes redirectAttributes, HttpSession session) {  
	    String memberId = (String) session.getAttribute("memberid");

	    // 기존 Diary 데이터 가져오기
	    Diary existingDiary = diaryService.getDiaryInfo(diary.getDiaryId());
	    if (existingDiary == null) {
	        redirectAttributes.addFlashAttribute("message", "수정할 일기를 찾을 수 없습니다.");
	        return "redirect:/diary/list/" + memberId;
	    }

	    // 1️⃣ 기존 데이터 유지
	    if (diary.getDiaryTitle() == null || diary.getDiaryTitle().trim().isEmpty()) {
	        diary.setDiaryTitle(existingDiary.getDiaryTitle());
	    }
	    if (diary.getDiaryContent() == null || diary.getDiaryContent().trim().isEmpty()) {
	        diary.setDiaryContent(existingDiary.getDiaryContent());
	    }
	    if (diary.getDiaryEmotion() == null || diary.getDiaryEmotion().trim().isEmpty()) {
	        diary.setDiaryEmotion(existingDiary.getDiaryEmotion());
	    }
	    if (diary.getDiaryFeed() == null || diary.getDiaryFeed().trim().isEmpty()) {
	        diary.setDiaryFeed(existingDiary.getDiaryFeed());
	    }

	    // 2️⃣ 일기 수정 시간 업데이트
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    diary.setDiaryUpdate(LocalDateTime.now().format(formatter));

	    try {
	        diaryService.updateDiary(diary);
	        redirectAttributes.addFlashAttribute("message", "수정되었습니다.");
	    } catch (RuntimeException e) {
	        e.printStackTrace();
	    }

	    return "redirect:/diary/" + diary.getDiaryId();
	}



	
}
