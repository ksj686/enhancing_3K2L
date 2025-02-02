package app.labs.emoji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;

import app.labs.emoji.service.EmojiService;

@Controller
@Slf4j
public class EmojiController {

    @Autowired
    EmojiService emojiService;

}
