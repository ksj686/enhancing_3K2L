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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService; // 알림 서비스 주입

    @GetMapping("/notice")
    @ResponseBody
    public List<Map<String, Object>> getNotice(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("memberid");
        noticeService.readNotice(memberId);
        List<Map<String, Object>> response = new ArrayList<>();
       

        try {
            List<Notice> noticeList = noticeService.getNoticeList(memberId);

            for (Notice notice : noticeList) {
                String message;
                String url;
                String noticeRead = notice.getNoticeRead();
                int noticeId = notice.getNoticeId();
                String noticeType = notice.getNoticeType();

                // 알림 타입에 따라 메시지 및 URL 설정
                if (noticeType.startsWith("DIARY_FEED")) {
                    message = memberId + "님의 일기에 메시지가 도착했어요.";
                    url = "/diary/Id/" + notice.getBoardId();
                } else if ("BOARD_HIDE_REPORT".equals(noticeType)) {
                    message = memberId + "님의 글이 신고 누적으로 인해 숨김 처리되었어요.";
                    url = "/emotion/Id/" + notice.getBoardId();
                } else if ("BOARD_HIDE_FILTER".equals(noticeType)) {
                    message = memberId + "님의 글이 부적절한 내용으로 인해 숨김 처리되었어요.";
                    url = "/emotion/Id/" + notice.getBoardId();
                } else if ("BOARD_REPORT".equals(noticeType)) {
                    message = memberId + "님의 글이 신고되었어요.";
                    url = "/emotion/Id/" + notice.getBoardId();
                } else {
                    message = "새로운 메시지가 도착했어요.";
                    url = "";
                }

                // 날짜 처리
                String dateMessage;
                if ("0".equals(notice.getNoticeDate())) {
                    dateMessage = "오늘";
                } else if ("1".equals(notice.getNoticeDate())) {
                    dateMessage = "어제";
                } else {
                    dateMessage = notice.getNoticeDate() + "일 전";
                }

                // 최종 메시지 생성
                Map<String, Object> content = new HashMap<>();
                content.put("message", message);
                content.put("date", dateMessage);
                content.put("url", url);
                content.put("noticeId", noticeId);
                content.put("noticeRead", noticeRead);

                response.add(content);

            }

        } catch (Exception e) {
            Map<String, Object> content = new HashMap<>();
            content.put("status", "ERROR");
            content.put("message", e.getMessage());

            response.add(content);
        }

        return response; // 최종 메시지 리스트 반환
    }


    /**
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
    }*/

    @DeleteMapping("/notice/delete/{noticeId}")
    public ResponseEntity<Map<String, String>> deleteNotice(@PathVariable int noticeId) {
        Map<String, String> response = new HashMap<>();
        try {
            noticeService.deleteNotice(noticeId);
            response.put("status", "OK");
            return ResponseEntity.ok(response); // 200 OK 응답
        } catch (Exception e) {
            response.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 오류 응답
        }
    }
}