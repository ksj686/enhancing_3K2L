package app.labs.notice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Notice {
    private int noticeId;
    private String noticeType; // "BOARD_REPORT", "BOARD_HIDE_REPORT", "BOARD_HIDE_FILTER", "DIARY_FEED"
    private String noticeSend; // "T" / "F"
    private String noticeRead; // "T" / "F"
    private String noticeDate;
    private int boardId;
    private String memberId;
}
