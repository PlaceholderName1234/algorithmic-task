package producer;

import queue.OrderQueue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class OrderProducerTest {

    private OrderQueue queue;
    private OrderProducer producer;
    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        queue = new OrderQueue();
        producer = new OrderProducer(queue);
        executor = Executors.newSingleThreadExecutor();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        producer.stop();
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    void testProducerCreatesOrders() throws InterruptedException {
        executor.submit(producer);

        Thread.sleep(500);

        assertTrue(producer.getOrdersCreated() > 0);
        assertTrue(queue.size() > 0);
    }

    @Test
    void testProducerStops() throws InterruptedException {
        executor.submit(producer);

        Thread.sleep(200);
        producer.stop();
        Thread.sleep(200);

        int ordersCreated = producer.getOrdersCreated();
        Thread.sleep(300);

        assertEquals(ordersCreated, producer.getOrdersCreated());
    }

    @Test
    void testGetOrdersCreated() {
        assertEquals(0, producer.getOrdersCreated());

        executor.submit(producer);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertTrue(producer.getOrdersCreated() >= 0);
    }
}