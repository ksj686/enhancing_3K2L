package app.labs.emoji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;

import app.labs.emoji.service.EmojiService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
public class EmojiController {

    @Autowired
    EmojiService emojiService;

    @PutMapping("/emoji")
    @ResponseBody
    public Map<String, Object> insertEmoji(@RequestParam int boardId,
                                           @RequestParam String emojiCategory,
                                           HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> emoji = new HashMap<>();
        try {
            String memberId = (String)request.getSession().getAttribute("memberid");
            emoji.put("emojiCategory", emojiCategory);
            emoji.put("memberId", memberId);
            emoji.put("boardId", boardId);
            emojiService.insertEmoji(emoji);

            response.put("status", "OK");
        } catch (Exception e) {
            log.error("emoji 추가 실패: ", e);
            response.put("status", "ERROR");
            response.put("message", "emoji 추가에 실패했습니다.");
        }
        return response;
    }
}
