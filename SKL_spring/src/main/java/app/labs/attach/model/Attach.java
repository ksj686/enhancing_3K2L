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
	private long attachSize;
	private String attachUrl;
	private String attachName;
	private int diaryId;
	
	// 🔹 파일 확장자 필드 추가
    public String getFileExtension() {
        if (attachName != null && attachName.contains(".")) {
            return attachName.substring(attachName.lastIndexOf(".") + 1).toLowerCase();
        }
        return "";
    }
}
