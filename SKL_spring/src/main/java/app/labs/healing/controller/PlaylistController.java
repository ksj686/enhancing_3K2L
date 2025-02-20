package app.labs.healing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import app.labs.healing.service.PlaylistService;
import app.labs.healing.model.Playlist;

import java.util.List;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    // 생성자 주입 방식 적용 (권장)
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/recommend")
    public String adminRecommendPlaylists(Model model) {
        String selectedKeyword = "경쾌한";

        // 특정 키워드를 포함하는 랜덤한 2개의 플레이리스트 조회
        List<Playlist> playlists = playlistService.getRandomPlaylists(selectedKeyword);

        // 모델에 플레이리스트 및 키워드 추가
        model.addAttribute("playlists", playlists);
        model.addAttribute("keyword", selectedKeyword);

        return "thymeleaf/healing/healing"; 
    }

    @GetMapping("/healing-contents")
    public String adminRecommendContents(Model model) {
        String selectedKeyword = "경쾌한";

        // 특정 키워드를 포함하는 랜덤한 4개의 플레이리스트 조회
        List<Playlist> playlists = playlistService.getFourRandomPlaylists(selectedKeyword);

        // 모델에 플레이리스트 및 키워드 추가
        model.addAttribute("playlists", playlists); // 속성명을 통일
        model.addAttribute("keyword", selectedKeyword);

        return "thymeleaf/healing/healing-contents";
    }
}

