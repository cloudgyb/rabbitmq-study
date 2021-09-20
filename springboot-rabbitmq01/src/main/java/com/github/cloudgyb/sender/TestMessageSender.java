package com.github.cloudgyb.sender;

import com.github.cloudgyb.constant.MQConstant;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author cloudgyb
 * 2021/7/4 13:39
 */
@Component
public class TestMessageSender {
    private final RabbitTemplate rabbitTemplate;

    public TestMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMess(String mess){
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE_TEST,MQConstant.ROUTING_KEY_TEST,
                mess,new CorrelationData("id$"+mess));
    }
}
