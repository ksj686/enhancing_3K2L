package app.labs.emoji.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmojiRepository {
    List<Map<String, Object>> getEmojiCount(int BoardId);
    void insertEmoji(Map<String, Object> emoji);
    int getEmoji(int boardId, String emojiCategory);
}
