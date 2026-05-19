package queue;

import model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderQueueTest {

    private OrderQueue queue;

    @BeforeEach
    void setUp() {
        queue = new OrderQueue();
    }

    @Test
    void testAddOrder() throws InterruptedException {
        Order order = Order.create("Тестов", "Товар", 5, false);
        queue.addOrder(order);
        assertEquals(1, queue.size());
        assertEquals(0, queue.urgentSize());
        assertEquals(1, queue.regularSize());
    }

    @Test
    void testUrgentPriority() throws InterruptedException {
        Order regularOrder = Order.create("Regular", "Товар", 5, false);
        Order urgentOrder = Order.create("Urgent", "Товар", 5, true);

        queue.addOrder(regularOrder);
        queue.addOrder(urgentOrder);

        Order taken = queue.takeOrder();
        assertNotNull(taken);
        assertTrue(taken.isUrgent());
    }
}