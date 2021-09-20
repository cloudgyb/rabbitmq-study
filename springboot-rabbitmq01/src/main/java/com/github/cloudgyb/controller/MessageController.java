package com.github.cloudgyb.controller;

import com.github.cloudgyb.sender.TestMessageSender2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cloudgyb
 * 2021/9/20 16:03
 */
@RestController
public class MessageController {
    private final TestMessageSender2 messageSender;

    public MessageController(TestMessageSender2 messageSender) {
        this.messageSender = messageSender;
    }

    @GetMapping("/send")
    public String sendMess(@RequestParam String mess){
        messageSender.sendMess(mess);
        return "ok";
    }
}
