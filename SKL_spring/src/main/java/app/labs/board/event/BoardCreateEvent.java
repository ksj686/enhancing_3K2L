package app.labs.board.event;

import app.labs.board.model.Board;
import lombok.Getter;

@Getter
public class BoardCreateEvent {

    private final Board board;
    
    public BoardCreateEvent(Board board) {
        this.board = board;
    }
}