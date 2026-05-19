import consumer.OrderConsumer;
import producer.OrderProducer;
import queue.OrderQueue;
import storage.OrderStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        System.out.println("Многопоточная система обработки заказов");
        System.out.println("=======================================");
        System.out.println();

        int minOrders = 30; // создастся не менее стольки заказов

        OrderQueue queue = new OrderQueue();
        OrderStorage storage = new OrderStorage();

        OrderProducer producer = new OrderProducer(queue);

        List<OrderConsumer> consumers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            consumers.add(new OrderConsumer("Consumer-" + i, queue, storage));
        }

        ExecutorService executor = Executors.newFixedThreadPool(consumers.size() + 1);

        executor.submit(producer);
        for (OrderConsumer consumer : consumers) {
            executor.submit(consumer);
        }

        while (producer.getOrdersCreated() < minOrders || queue.size() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        producer.stop();
        for (OrderConsumer consumer : consumers) {
            consumer.stop();
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        storage.printStatistics();
    }
}