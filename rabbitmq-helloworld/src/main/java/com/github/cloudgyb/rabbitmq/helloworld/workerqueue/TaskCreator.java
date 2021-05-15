package com.github.cloudgyb.rabbitmq.helloworld.workerqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @author cloudgyb
 * 2021/5/14 14:59
 */
public class TaskCreator {
    private final ConnectionFactory connectionFactory;
    private final String QUEUE_NAME;

    public TaskCreator(ConnectionFactory connectionFactory, String queue_name) {
        this.connectionFactory = connectionFactory;
        QUEUE_NAME = queue_name;
    }

    public void publishTask(String task) {
        try (
                Connection conn = connectionFactory.newConnection();
                final Channel channel = conn.createChannel()
        ) {
            channel.basicPublish("", QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, task.getBytes());
            System.out.println("create new task: '" + task + "'");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
