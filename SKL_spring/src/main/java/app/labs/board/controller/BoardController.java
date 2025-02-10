package app.labs.board.controller;

import app.labs.emoji.service.EmojiService;
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
@RequestMapping("/emo")
public class BoardController {

	@Autowired
	BoardService boardService;

	@Autowired
	EmojiService emojiService;

	@GetMapping(value= {"", "/"})
	public String boardMainWW() {
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
	public String getBoardInfo(Model model, @PathVariable("boardId") int boardId) {
		Board board = boardService.getBoardInfo(boardId);
		if (board != null) {
			if ("F".equals(board.getBoardOffensive())) {
				// 이모지 데이터 변환
				List<Map<String, Object>> emojiList = emojiService.getEmojiCount(boardId);
				Map<String, Integer> emojiMap = new HashMap<>();
				for (Map<String, Object> emoji : emojiList) {
					String category = (String) emoji.get("EMOJI_CATEGORY");
					Number countNum = (Number) emoji.get("COUNT");
					Integer count = countNum != null ? countNum.intValue() : 0;
					emojiMap.put(category, count);
				}
				// 모든 이모지 카테고리에 대해 기본값 0 설정
				String[] categories = {"joy", "cheer", "worry", "sad"};
				for (String category : categories) {
					emojiMap.putIfAbsent(category, 0);
				}
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
	public String createBoard(Model model, @PathVariable("boardCategory") String boardCategory) {
		model.addAttribute("board", new Board());
		model.addAttribute("boardCategory", boardCategory);
		return "thymeleaf/board/board_new";
	}

	@PostMapping("/{boardCategory}/new")
	public String createBoard(Board board, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute("memberid");
		if (memberId != null) {
			int boardId = boardService.createBoardId();
			board.setMemberId(memberId);
			board.setBoardId(boardId);
			boardService.createBoard(board);
			return "redirect:/emo/Id/" + boardId;
		} else {
			return "redirect:/login";
		}
	}

	@PutMapping("/report")
	@ResponseBody
	public Map<String, Object> reportBoard(@RequestParam int boardId, HttpServletRequest request) {
		// 응답에 status와 boardReport 추가
        Map<String, Object> response = new HashMap<>();
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute("memberid");
		try {
			boardService.reportBoard(boardId);
			int boardReport = boardService.getBoardReport(boardId);
			if (boardReport >= 5) {
				boardService.offensiveBoard(boardId);
			}
			response.put("status", "OK");
			response.put("boardReport", boardReport);
		} catch (Exception e) {
			response.put("status", "ERROR");
			response.put("message", e.getMessage());
		}
        return response;
    }
}