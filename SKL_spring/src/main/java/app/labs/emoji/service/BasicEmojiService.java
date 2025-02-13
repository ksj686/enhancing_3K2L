package app.labs.emoji.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.emoji.dao.EmojiRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasicEmojiService implements EmojiService {

    @Autowired
    private EmojiRepository emojiRepository;

    @Override
    public List<Map<String, Object>> getEmojiCount(int BoardId) {
        return emojiRepository.getEmojiCount(BoardId);
    }

    @Override
    public void insertEmoji(Map<String, Object> emoji) {
        emojiRepository.insertEmoji(emoji);
    }

    // Map 형식 변환
    @Override
    public Map<String, Integer> getEmojiMap(int BoardId) {
        List<Map<String, Object>> emojiList = getEmojiCount(BoardId);
        Map<String, Integer> emojiMap = new HashMap<>();
        for (Map<String, Object> emoji : emojiList) {
            String category = (String) emoji.get("EMOJI_CATEGORY");
            Number countNum = (Number) emoji.get("COUNT");
            Integer count = countNum != null ? countNum.intValue() : 0;
            emojiMap.put(category, count);
        }
        // 모든 이모지 카테고리에 대해 기본값 0 설정
        String[] categories = {"joy", "cheer", "worry", "sad"};
        for (String category : categories) {
            emojiMap.putIfAbsent(category, 0);
        }
        return emojiMap;
    }

    @Override
    public int getEmoji(int boardId, String emojiCategory) {
        return emojiRepository.getEmoji(boardId, emojiCategory);
    }
}