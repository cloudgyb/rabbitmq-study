package com.github.cloudgyb.rabbitmq.helloworld.workerqueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

/**
 * @author cloudgyb
 * 2021/5/14 15:10
 */
public class TaskWorker implements DeliverCallback {
    private final Object shutLock = new Object();
    private final String QUEUE_NAME;
    private final Connection connection;
    private Channel channel;

    public TaskWorker(String QUEUE_NAME, Connection connection) {
        this.QUEUE_NAME = QUEUE_NAME;
        this.connection = connection;
        try {
            this.channel = connection.createChannel();
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }

    public void init() {
        try {
            channel.basicQos(1);
            channel.basicConsume(QUEUE_NAME, false, this, consumerTag -> {
                System.out.println("----------");
                shutdown();
            });
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            if(this.channel.isOpen() || this.connection.isOpen()) {
                synchronized (shutLock) {
                    if (this.channel.isOpen()) {
                        this.channel.close();
                    }
                    if (this.connection.isOpen()) {
                        this.connection.close();
                    }
                }
            }
        }catch (IOException | TimeoutException ie){
            ie.printStackTrace();
        }
    }

    @Override
    public void handle(String consumerTag, Delivery message) throws IOException {
        final String task = new String(message.getBody());
        System.out.println("Received a new task:'" + task + "'");
        try {
            doWork(task);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            final long deliveryTag = message.getEnvelope().getDeliveryTag();
            channel.basicAck(deliveryTag, false);
        }
    }

    private void doWork(String task) throws InterruptedException {
        System.out.println("do task :" + task);
        Thread.sleep(2000);
    }

}
