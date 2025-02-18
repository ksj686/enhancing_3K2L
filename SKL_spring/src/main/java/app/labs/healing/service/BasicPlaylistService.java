package app.labs.healing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.healing.dao.PlaylistRepository;
import app.labs.healing.model.Playlist;

@Service
public class BasicPlaylistService implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Override
    public List<Playlist> getRandomPlaylists(String keyword) {
        return playlistRepository.getRandomPlaylists(keyword);
    }
    
    @Override
    public List<Playlist> getRandomPlaylistsFour(String keyword) {
        return playlistRepository.getRandomPlaylistsFour(keyword);
    }
}
