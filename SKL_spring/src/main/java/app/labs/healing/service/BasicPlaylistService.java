package app.labs.healing.service;

import java.util.List;
import java.util.Collections;

import org.springframework.stereotype.Service;
import app.labs.healing.dao.PlaylistRepository;
import app.labs.healing.model.Playlist;

@Service
public class BasicPlaylistService implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    // 생성자 주입 방식 적용 (권장)
    public BasicPlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public List<Playlist> getRandomPlaylists(String keyword) {
        List<Playlist> playlists = playlistRepository.getRandomPlaylists(keyword);
        return (playlists != null) ? playlists : Collections.emptyList();
    }
    
    @Override
    public List<Playlist> getFourRandomPlaylists(String keyword) {
        List<Playlist> playlists = playlistRepository.getFourRandomPlaylists(keyword);
        return (playlists != null) ? playlists : Collections.emptyList();
    }
}
