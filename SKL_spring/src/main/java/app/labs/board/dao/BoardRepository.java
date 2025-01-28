package app.labs.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.board.model.Board;

@Mapper
public interface BoardRepository {
	List<Board> getBoardList(@Param("boardCategory") String boardCategory);
	Board getBoardInfo(int boardId);
	int createBoardId();
	void createBoard(Board board);
	void updateBoard(Board board);
	// 삭제 기능은 없음 
}
