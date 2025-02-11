package app.labs.admin.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Events {
    private int eventId;
    private String eventName;
    private String eventDescription;
    private String eventCreatedAt;
}
