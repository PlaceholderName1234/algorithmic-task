package service;

import consumer.OrderConsumer;
import producer.OrderProducer;
import queue.OrderQueue;
import storage.OrderStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskManagerService {
    private final OrderQueue queue;
    private final OrderStorage storage;
    private final OrderProducer producer;
    private final List<OrderConsumer> consumers;
    private ExecutorService executor;

    public TaskManagerService() {
        this.queue = new OrderQueue();
        this.storage = new OrderStorage();
        this.producer = new OrderProducer(queue);
        this.consumers = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            consumers.add(new OrderConsumer("Consumer-" + i, queue, storage));
        }
    }

    public void start() {
        executor = Executors.newFixedThreadPool(consumers.size() + 1);

        executor.submit(producer);
        for (OrderConsumer consumer : consumers) {
            executor.submit(consumer);
        }

        System.out.println("TaskManagerService: система запущена");
    }

    public void waitForCompletion(int minOrders) {
        while (producer.getOrdersCreated() < minOrders || queue.size() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        producer.stop();
        for (OrderConsumer consumer : consumers) {
            consumer.stop();
        }

        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        storage.printStatistics();
    }
}