package app.labs.emoji.service;

import java.util.List;
import java.util.Map;

public interface EmojiService {
    List<Map<String, Object>> getEmojiCount(int BoardId);
    void insertEmoji(Map<String, Object> emoji);
}