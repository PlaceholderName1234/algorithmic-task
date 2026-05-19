package consumer;

import model.Order;
import queue.OrderQueue;
import storage.OrderStorage;
import validator.OrderValidator;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderConsumer implements Runnable {
    private final String name;
    private final OrderQueue queue;
    private final OrderStorage storage;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Random random = new Random();
    private final AtomicInteger ordersProcessed = new AtomicInteger(0);

    public OrderConsumer(String name, OrderQueue queue, OrderStorage storage) {
        this.name = name;
        this.queue = queue;
        this.storage = storage;
    }

    @Override
    public void run() {
        System.out.println("Consumer '" + name + "' запущен");

        while (running.get()) {
            try {
                Order order = queue.takeOrder();
                if (order == null) {
                    continue;
                }

                var validationErrors = OrderValidator.validate(order);
                if (!validationErrors.isEmpty()) {
                    System.err.println("Consumer " + name + ": Ошибка валидации заказа #" +
                            order.getId() + ": " + validationErrors);
                    order.setStatus("INVALID");
                    continue;
                }

                System.out.println("Consumer " + name + " начал обработку заказа #" + order.getId() +
                        (order.isUrgent() ? " (СРОЧНЫЙ)" : ""));

                long processingTime = order.isUrgent() ?
                        random.nextInt(500) + 100 :
                        random.nextInt(1000) + 500;

                Thread.sleep(processingTime);

                order.setProcessedAt(LocalDateTime.now());
                order.setStatus("PROCESSED");
                storage.addProcessedOrder(order);
                ordersProcessed.incrementAndGet();

                System.out.println("Consumer " + name + " завершил обработку заказа #" + order.getId() +
                        " за " + processingTime + " мс");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("Consumer '" + name + "' остановлен");
    }

    public void stop() {
        running.set(false);
    }

    public int getOrdersProcessed() {
        return ordersProcessed.get();
    }

    public String getConsumerName() {
        return name;
    }
}