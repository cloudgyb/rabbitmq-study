package com.github.cloudgyb;

import com.github.cloudgyb.sender.TestMessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author cloudgyb
 * 2021/7/4 13:52
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SendMessageTest {
    @Autowired
    private TestMessageSender testMessageSender;

    @Test
    public void test() {
        testMessageSender.sendMess("hello");
    }
}
