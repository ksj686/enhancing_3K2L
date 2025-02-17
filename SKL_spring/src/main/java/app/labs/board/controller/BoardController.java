package app.labs.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import app.labs.board.model.Board;
import app.labs.board.service.BoardService;
import app.labs.emoji.service.EmojiService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/emotion")
public class BoardController {

	@Autowired
	BoardService boardService;

	@Autowired
	EmojiService emojiService;

	@GetMapping(value= {"", "/"})
	public String boardMain(Model model) {
		int boardCount = boardService.getBoardCount();
		model.addAttribute("boardCount", boardCount);
		return "thymeleaf/board/board_main";
	}

	@GetMapping("/{boardCategory}")
	public String boardCategory(Model model, @PathVariable("boardCategory") String boardCategory) {
		model.addAttribute("category", boardCategory);
		List<Board> boardList = boardService.getBoardList(boardCategory);
		model.addAttribute("boardList", boardList);
		return "thymeleaf/board/board_category";
	}

	@GetMapping("/Id/{boardId}")
	public String getBoardInfo(Model model, @PathVariable("boardId") int boardId, HttpServletRequest request) {
		Board board = boardService.getBoardInfo(boardId);
		HttpSession session = request.getSession();
		String sessionId = (String)session.getAttribute("memberid");
		if (board != null) {
			if ("F".equals(board.getBoardOffensive()) || sessionId.equals(board.getMemberId())) {
				// 이모지 데이터 변환
				Map<String, Integer> emojiMap = emojiService.getEmojiMap(boardId);
				model.addAttribute("board", board);
				model.addAttribute("emoji", emojiMap);
				log.info("emoji" + emojiMap);
				return "thymeleaf/board/board_details";
			} else {
				return "thymeleaf/board/board_offensive";
			}
		} else {
			return "thymeleaf/board/board_not_found";
		}
	}

	@GetMapping("/{boardCategory}/new")
	public String createBoard(Model model,
							  @PathVariable("boardCategory") String boardCategory,
							  HttpServletRequest request) {
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute("memberid");
		if (memberId != null) {
			model.addAttribute("board", new Board());
			model.addAttribute("boardCategory", boardCategory);
			return "thymeleaf/board/board_new";
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping("/{boardCategory}/new")
	public String createBoard(Board board, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute("memberid");
		int boardId = boardService.createBoardId();
		board.setMemberId(memberId);
		board.setBoardId(boardId);
		boardService.createBoard(board);
		return "redirect:/emotion/Id/" + boardId;
	}

	@PutMapping("/report")
	@ResponseBody
	public Map<String, Object> reportBoard(@RequestParam int boardId) {
		// 응답에 status와 boardReport 추가
        Map<String, Object> response = new HashMap<>();
        try {
            boardService.reportBoard(boardId);
            response.put("status", "OK");
            response.put("boardReport", boardService.getBoardReport(boardId));
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
        }
        return response;
    }
}