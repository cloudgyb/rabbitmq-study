package com.github.cloudgyb.consumer;

import java.io.IOException;
import java.util.Map;

import com.github.cloudgyb.constant.MQConstant;
import com.rabbitmq.client.Channel;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cloudgyb
 * 2021/7/4 17:42
 */
@Component
public class TestMessageConsumer {

    @RabbitListener(
            bindings = {
                @QueueBinding(
                    value = @Queue(MQConstant.QUEUE_TEST),
                    exchange = @Exchange(value=MQConstant.EXCHANGE_TEST,type = "fanout"),
                    key = MQConstant.ROUTING_KEY_TEST
                )
            }
    )
    @RabbitHandler(isDefault = true)
    @Transactional
    public void onTestMessage(@Payload String mess,@Headers Map<String,Object> headers,
            Channel channel) throws IOException {
        System.out.println("1"+channel);
        final long deliveryTag = (long)headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("(consumer1)消费消息："+mess+" deliveryTag："+deliveryTag);
        channel.basicAck(deliveryTag,false);
    }
}
