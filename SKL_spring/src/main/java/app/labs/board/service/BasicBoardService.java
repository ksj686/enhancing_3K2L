package app.labs.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.board.dao.BoardRepository;

@Service
public class BasicBoardService implements BoardService {
	
	@Autowired
	BoardRepository boardRepository;
}
