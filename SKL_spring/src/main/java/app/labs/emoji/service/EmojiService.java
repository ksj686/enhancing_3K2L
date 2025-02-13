package app.labs.emoji.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public interface EmojiService {
    List<Map<String, Object>> getEmojiCount(int BoardId);
    Map<String, Integer> getEmojiMap(int EmojiId);
    void insertEmoji(Map<String, Object> emoji);
    int getEmoji(int boardId, String emojiCategory);
}