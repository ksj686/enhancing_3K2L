package app.labs.board.event;

import lombok.Getter;

@Getter
public class BoardReportedEvent {

    private final int boardId;
    
    public BoardReportedEvent(int boardId) {
        this.boardId = boardId;
    }
}