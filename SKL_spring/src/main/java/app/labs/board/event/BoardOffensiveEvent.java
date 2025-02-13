package app.labs.board.event;

import lombok.Getter;

@Getter
public class BoardOffensiveEvent {

    private final int boardId;
    private final boolean boardReported;
    
    public BoardOffensiveEvent(int boardId, boolean boardReported) {
        this.boardId = boardId;
        this.boardReported = boardReported;
    }
}
