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

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/emo")
public class BoardController {

	@Autowired
	BoardService boardService;

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
		model.addAttribute("board", board);
		return "thymeleaf/board/board_details" ;
	}

	@GetMapping("/{boardCategory}/new")
	public String createBoard(Model model, @PathVariable("boardCategory") String boardCategory) {
		model.addAttribute("board", new Board());
		model.addAttribute("boardCategory", boardCategory);
		return "thymeleaf/board/board_new";
	}

	@PostMapping("/{boardCategory}/new")
	public String createBoard(Board board, HttpServletRequest request,
							  RedirectAttributes redirectAttributes) {
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
	public Map<String, Object> reportBoard(@RequestParam int boardId) {
		boardService.reportBoard(boardId);
		int countReport = boardService.countReportBoard(boardId);
        if (countReport >= 5) {
            boardService.offensiveBoard(boardId);
        }
        
		        // 응답에 status와 reportCount 추가
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("reportCount", countReport);
        
        return response;
    }
}