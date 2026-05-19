package storage;

import model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStorageTest {

    private OrderStorage storage;
    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        storage = new OrderStorage();
        order1 = Order.create("Иванов", "Ноутбук", 10, false);
        order2 = Order.create("Петров", "Телефон", 5, true);
    }

    @Test
    void testAddAndGetOrder() {
        storage.addProcessedOrder(order1);
        storage.addProcessedOrder(order2);

        assertEquals(order1, storage.getOrder(order1.getId()));
        assertEquals(order2, storage.getOrder(order2.getId()));
    }

    @Test
    void testTotalProcessed() {
        assertEquals(0, storage.getTotalProcessed());

        storage.addProcessedOrder(order1);
        assertEquals(1, storage.getTotalProcessed());

        storage.addProcessedOrder(order2);
        assertEquals(2, storage.getTotalProcessed());
    }

    @Test
    void testUrgentProcessed() {
        storage.addProcessedOrder(order1);
        assertEquals(0, storage.getUrgentProcessed());

        storage.addProcessedOrder(order2);
        assertEquals(1, storage.getUrgentProcessed());
    }

    @Test
    void testGetAllOrders() {
        storage.addProcessedOrder(order1);
        storage.addProcessedOrder(order2);

        var allOrders = storage.getAllOrders();
        assertEquals(2, allOrders.size());
        assertTrue(allOrders.containsKey(order1.getId()));
        assertTrue(allOrders.containsKey(order2.getId()));
    }
}