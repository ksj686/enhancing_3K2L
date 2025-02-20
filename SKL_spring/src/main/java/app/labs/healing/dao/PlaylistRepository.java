package app.labs.healing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.healing.model.Playlist;

@Mapper
public interface PlaylistRepository {

    // 특정 키워드를 포함하는 랜덤한 2개의 플레이리스트 조회
    List<Playlist> getRandomPlaylists(@Param("keyword") String keyword);
    // 특정 키워드를 포함하는 랜덤한 4개의 플레이리스트 조회
    List<Playlist> getFourRandomPlaylists(@Param("keyword") String keyword);
}
    
