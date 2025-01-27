package app.labs.attach.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

public class Attach {
	private int attachId;
	private String attachSize;
	private String attachUrl;
	private String attachName;
	private int diaryId;
}
