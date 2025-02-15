package app.labs.board.event;

import app.labs.board.model.Board;
import lombok.Getter;

@Getter
public class BoardCreateEvent {

    private final Board board;
    
    public BoardCreateEvent(Board board) {
        this.board = board; // Offensive 사유: (true)신고 / (false)필터링
    }
}