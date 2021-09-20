package com.github.cloudgyb.config;

import javax.annotation.PostConstruct;

import com.github.cloudgyb.constant.MQConstant;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cloudgyb
 * 2021/7/4 12:47
 */
@Configuration
public class QueueConfig {
    private final RabbitTemplate rabbitTemplate;

    public QueueConfig(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void initRabbitMQ() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            assert correlationData != null;
            final String id = correlationData.getId();
            if (ack) {
                System.out.println("消息发送成功,id=" + id);
            }
            else {
                System.out.println("消息发送失败,id=" + id);
                System.out.println(cause);
            }
        });
    }

    /**
     * 定义该bean，让Spring帮我们自动创建队列
     */
    @Bean
    public Queue queue() {
        return new Queue(MQConstant.QUEUE_TEST, true);
    }

    /**
     * 定义该bean，让Spring帮我们自动创建交换机
     */
    @Bean
    public Exchange exchange() {
        return new TopicExchange(MQConstant.EXCHANGE_TEST);
    }

    @Bean
    public Binding binding() {
        return new Binding(MQConstant.QUEUE_TEST, Binding.DestinationType.QUEUE,
                MQConstant.EXCHANGE_TEST, MQConstant.ROUTING_KEY_TEST, null);
    }
}
