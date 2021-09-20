package com.github.cloudgyb.sender;

import com.github.cloudgyb.constant.MQConstant;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author cloudgyb
 * 2021/9/20 16:25
 */
@Component
public class TestMessageSender2 {
    private final RabbitTemplate rabbitTemplate;

    public TestMessageSender2(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMess(String mess){
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE_TEST,MQConstant.ROUTING_KEY_TEST,
                mess,new CorrelationData("id$"+mess));
    }
}
