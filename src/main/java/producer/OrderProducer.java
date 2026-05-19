package producer;

import model.Order;
import queue.OrderQueue;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderProducer implements Runnable {
    private final OrderQueue queue;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Random random = new Random();
    private final AtomicInteger ordersCreated = new AtomicInteger(0);

    private final String[] customers = {
            "Иванов", "Смирнов", "Кузнецов", "Попов", "Васильев",
            "Петров", "Соколов", "Михайлов", "Новиков", "Федоров"
    };

    private final String[] products = {
            "Ноутбук", "Телефон", "Клавиатура", "Мышь", "Монитор",
            "Наушники", "Колонки", "Камера", "Микрофон", "Кресло"
    };

    public OrderProducer(OrderQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Producer запущен.");

        while (running.get()) {
            try {
                String customer = customers[random.nextInt(customers.length)];
                String product = products[random.nextInt(products.length)];
                int quantity = random.nextInt(100) + 1;
                boolean isUrgent = random.nextInt(100) < 20;

                Order order = Order.create(customer, product, quantity, isUrgent);
                queue.addOrder(order);
                ordersCreated.incrementAndGet();

                Thread.sleep(random.nextInt(400) + 100);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("Producer остановлен. Всего создано заказов: " + ordersCreated.get());
    }

    public void stop() {
        running.set(false);
    }

    public int getOrdersCreated() {
        return ordersCreated.get();
    }
}