package app.labs.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.labs.board.model.Board;
import app.labs.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/emo")
public class BoardController {

	@Autowired
	BoardService boardService;

	@GetMapping(value= {"", "/"})
	public String boardMain() {
		return "thymeleaf/board/board_main";
	}

	@GetMapping("/{boardCategory}")
	public String boardCategory(Model model, @PathVariable("boardCategory") String boardCategory) {
		model.addAttribute("category", boardCategory);
		return "thymeleaf/board/board_category";
	}

	@GetMapping("/{boardCategory}/new")
	public String createBoard(Model model, @PathVariable("boardCategory") String boardCategory) {
		model.addAttribute("board", new Board());
		model.addAttribute("boardCategory", boardCategory);
		return "thymeleaf/board/board_new";
	}
}
