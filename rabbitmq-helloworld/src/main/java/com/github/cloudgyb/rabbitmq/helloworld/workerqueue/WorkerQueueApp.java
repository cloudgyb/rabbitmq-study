package com.github.cloudgyb.rabbitmq.helloworld.workerqueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author cloudgyb
 * 2021/5/14 15:00
 */
public class WorkerQueueApp {
    private final static String QUEUE_NAME = "test";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        }
        catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        final Thread creatorThread = new Thread(() -> {
            final TaskCreator taskCreator = new TaskCreator(factory, QUEUE_NAME);
            taskCreator.publishTask("task-1");
            taskCreator.publishTask("task-2");
            taskCreator.publishTask("task-3");
        });
        creatorThread.start();

        final Thread workerThread = new Thread(() -> {
            final TaskWorker worker;
            try {
                worker = new TaskWorker(QUEUE_NAME, factory.newConnection());
                worker.init();
            }
            catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        });
        workerThread.start();

        final Thread shutdownHookThread = new Thread(() -> {
            workerThread.interrupt();
            creatorThread.interrupt();
            System.out.println("系统退出！");
        });
        Runtime.getRuntime().addShutdownHook(shutdownHookThread);
    }
}
