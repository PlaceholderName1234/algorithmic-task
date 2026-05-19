package queue;

import model.Order;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class OrderQueue {
    private final BlockingQueue<Order> urgentQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Order> regularQueue = new LinkedBlockingQueue<>();

    public void addOrder(Order order) throws InterruptedException {
        if (order.isUrgent()) {
            urgentQueue.put(order);
            System.out.println("СРОЧНЫЙ заказ добавлен в очередь: " + order);
        } else {
            regularQueue.put(order);
            System.out.println("Обычный заказ добавлен в очередь: " + order);
        }
    }

    public Order takeOrder() throws InterruptedException {
        Order urgentOrder = urgentQueue.poll();
        if (urgentOrder != null) {
            return urgentOrder;
        }
        return regularQueue.poll(100, TimeUnit.MILLISECONDS);
    }

    public int size() {
        return urgentQueue.size() + regularQueue.size();
    }

    public int urgentSize() {
        return urgentQueue.size();
    }

    public int regularSize() {
        return regularQueue.size();
    }
}