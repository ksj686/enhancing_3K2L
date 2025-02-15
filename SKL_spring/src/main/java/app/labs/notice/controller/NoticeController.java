package app.labs.notice.controller;

import app.labs.notice.model.Notice;
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

import java.util.List;

@Slf4j
@Controller
public class NoticeController {

    @Autowired
    private NoticeService notificeService; // 알림 서비스 주입

    @GetMapping("/notice")
    public String getNotice(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute("memberid");
        List<Notice> noticeList = notificeService.getNoticeList(memberId);
        int unreadCount = notificeService.countNotice(memberId);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("unreadCount", unreadCount);
        notificeService.readNotice(memberId);
        return "thymeleaf/notice/notice_list";
    }

    @DeleteMapping("/notice/delete/{noticeId}")
    public void deleteNotice(@PathVariable("noticeId") int noticeId) {
        notificeService.deleteNotice(noticeId);
    }
}
