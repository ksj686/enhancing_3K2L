package app.labs.diary.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Diary {
	
	private int diaryId;
	private String diaryTitle;
	private String diaryDate;
	private String diaryContent;
	private int diaryVol;
	private String diaryFeed;
	private String diaryEmotion;
	private String memberId;
	private String memberNickname;

	private int month;
	private int year;
}
