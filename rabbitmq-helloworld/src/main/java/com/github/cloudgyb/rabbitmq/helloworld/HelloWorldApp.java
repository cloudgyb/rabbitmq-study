package com.github.cloudgyb.rabbitmq.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Hello world for rabbitmq!
 *
 * @author geng
 */
public class HelloWorldApp {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("test", false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", "test", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
    }
}
