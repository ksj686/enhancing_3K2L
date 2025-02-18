package app.labs.healing.service;

import java.util.List;

import app.labs.healing.model.Playlist;

public interface PlaylistService {
    // 특정 키워드를 포함하는 랜덤한 2개의 플레이리스트 조회
    List<Playlist> getRandomPlaylists(String keyword);
    // 특정 키워드를 포함하는 랜덤한 4개의 플레이리스트 조회
    List<Playlist> getRandomPlaylistsFour(String keyword);
    
}
