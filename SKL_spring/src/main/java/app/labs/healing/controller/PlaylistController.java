package app.labs.healing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.labs.healing.model.Playlist;
import app.labs.healing.service.PlaylistService;

import java.util.List;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

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

        // 특정 키워드를 포함하는 랜덤한 2개의 플레이리스트 조회
        List<Playlist> playlists = playlistService.getRandomPlaylistsFour(selectedKeyword);

        // 모델에 플레이리스트 및 키워드 추가
        model.addAttribute("playlists", playlists);
        model.addAttribute("keyword", selectedKeyword);
    	
    	return "thymeleaf/healing/healing-contents";

    }
    	
}
