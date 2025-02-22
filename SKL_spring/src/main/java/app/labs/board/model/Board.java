package app.labs.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Board {
	private int boardId;
	private String boardTitle;
	private String boardCategory;
	private String boardDate;      // TIMESTAMP
	private String boardContent;   // CLOB
	private String boardOffensive; // "T" | "F"
	private int boardReport;       // 신고 누적수
	private String memberId;
	private String memberNickname;
	// DTO
}