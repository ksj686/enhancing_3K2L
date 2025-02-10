package app.labs.emoji.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.emoji.dao.EmojiRepository;

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

}