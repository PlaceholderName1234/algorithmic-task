package storage;

import lombok.Synchronized;
import model.Order;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderStorage {
    private final ConcurrentHashMap<Long, Order> processedOrders = new ConcurrentHashMap<>();
    private final AtomicInteger totalProcessed = new AtomicInteger(0);
    private final AtomicInteger urgentProcessed = new AtomicInteger(0);

    @Synchronized
    public void addProcessedOrder(Order order) {
        processedOrders.put(order.getId(), order);
        totalProcessed.incrementAndGet();
        if (order.isUrgent()) {
            urgentProcessed.incrementAndGet();
        }
    }

    public Order getOrder(Long id) {
        return processedOrders.get(id);
    }

    public int getTotalProcessed() {
        return totalProcessed.get();
    }

    public int getUrgentProcessed() {
        return urgentProcessed.get();
    }

    public ConcurrentHashMap<Long, Order> getAllOrders() {
        return new ConcurrentHashMap<>(processedOrders);
    }

    @Synchronized
    public void printStatistics() {
        System.out.println("\n==============================");
        System.out.println("Статистика");
        System.out.println("==============================");
        System.out.println("Всего обработано заказов: " + totalProcessed.get());
        System.out.println("Срочных заказов: " + urgentProcessed.get());
        System.out.println("Обычных заказов: " + (totalProcessed.get() - urgentProcessed.get()));
    }
}