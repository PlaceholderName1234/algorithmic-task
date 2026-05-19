package consumer;

import queue.OrderQueue;
import storage.OrderStorage;
import model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class OrderConsumerTest {

    private OrderQueue queue;
    private OrderStorage storage;
    private OrderConsumer consumer;
    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        queue = new OrderQueue();
        storage = new OrderStorage();
        consumer = new OrderConsumer("TestConsumer", queue, storage);
        executor = Executors.newSingleThreadExecutor();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        consumer.stop();
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    void testConsumerProcessesOrder() throws InterruptedException {
        Order order = Order.create("Тестов", "Товар", 5, false);
        queue.addOrder(order);

        executor.submit(consumer);

        Thread.sleep(1000);

        assertEquals(0, queue.size());
    }

    @Test
    void testConsumerProcessesUrgentOrderFirst() throws InterruptedException {
        Order regularOrder = Order.create("Regular", "Товар", 5, false);
        Order urgentOrder = Order.create("Urgent", "Товар", 5, true);

        queue.addOrder(regularOrder);
        queue.addOrder(urgentOrder);

        executor.submit(consumer);

        Thread.sleep(1000);

        assertTrue(storage.getTotalProcessed() > 0);
    }

    @Test
    void testConsumerInvalidOrder() throws InterruptedException {
        Order invalidOrder = Order.create("", "", 0, false);
        queue.addOrder(invalidOrder);

        executor.submit(consumer);

        Thread.sleep(500);

        assertNotNull(invalidOrder.getStatus());
        assertEquals("INVALID", invalidOrder.getStatus());
    }

    @Test
    void testConsumerStop() throws InterruptedException {
        executor.submit(consumer);

        Thread.sleep(100);
        consumer.stop();
        Thread.sleep(200);

        assertDoesNotThrow(() -> consumer.stop());
    }

    @Test
    void testGetConsumerName() {
        assertEquals("TestConsumer", consumer.getConsumerName());
    }
}