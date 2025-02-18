package app.labs.healing.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Playlist {
    private int playlistId;             // 플레이리스트 ID
    private String playlistTitle;		// 제목
    private String playlistUrl;   
    private String playlistTag;        // 참고 태그

}
