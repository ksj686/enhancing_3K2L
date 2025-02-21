package app.labs.diary.event;

import app.labs.diary.model.Diary;
import lombok.Getter;

@Getter
public class DiaryCreateEvent {

    private final Diary diary;

    public DiaryCreateEvent(Diary diary) { this.diary = diary; }
}
