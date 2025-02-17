package app.labs.notice.controller;

import app.labs.notice.model.Notice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import app.labs.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class NoticeController {

    @Autowired
    private NoticeService notificeService; // 알림 서비스 주입

    @GetMapping("/notice")
    public String getNotice(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("memberid");
        if (memberId != null) {
            List<Notice> noticeList = notificeService.getNoticeList(memberId);
            int unreadCount = notificeService.countNotice(memberId);
            model.addAttribute("noticeList", noticeList);
            model.addAttribute("unreadCount", unreadCount);
            notificeService.readNotice(memberId);
            return "thymeleaf/notice/notice_list";
        } else {
            return "redirect:/login";
        }

    }

    @DeleteMapping("/notice/delete/{noticeId}")
    public ResponseEntity<Map<String, String>> deleteNotice(@PathVariable int noticeId) {
        Map<String, String> response = new HashMap<>();
        try {
            notificeService.deleteNotice(noticeId);
            response.put("status", "OK");
            return ResponseEntity.ok(response); // 200 OK 응답
        } catch (Exception e) {
            response.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 오류 응답
        }
    }
}