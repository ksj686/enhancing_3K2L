package app.labs.emoji.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.emoji.dao.EmojiRepository;

@Service
public class BasicEmojiService implements EmojiService {

    @Autowired
    private EmojiRepository emojiRepository;
}