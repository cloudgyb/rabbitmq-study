package com.github.cloudgyb.consumer;

import java.io.IOException;
import java.util.Map;

import com.github.cloudgyb.constant.MQConstant;
import com.rabbitmq.client.Channel;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author cloudgyb
 * 2021/7/4 17:42
 */
@Component
public class TestMessageConsumer {

    @RabbitListener(queues = MQConstant.QUEUE_TEST)
    @RabbitHandler(isDefault = true)
    public void onTestMessage(@Payload String mess,@Headers Map<String,Object> headers,
            Channel channel) throws IOException {
        final long deliveryTag = (long)headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("(consumer1)消费消息："+mess+" deliveryTag："+deliveryTag);
        channel.basicAck(deliveryTag,false);
    }
}
