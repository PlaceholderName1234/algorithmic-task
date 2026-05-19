package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order1;

    @BeforeEach
    void setUp() {
        order1 = Order.create("Иванов", "Ноутбук", 10, false);
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(order1, order1);
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(null, order1);
    }

    @Test
    void testEqualsDifferentClass() {
        assertNotEquals("строка", order1);
        assertNotEquals(123, order1);
    }

    @Test
    void testHashCodeConsistency() {
        int hashCode1 = order1.hashCode();
        int hashCode2 = order1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testToStringNotNull() {
        assertNotNull(order1.toString());
        assertFalse(order1.toString().isEmpty());
    }

    @Test
    void testGetters() {
        assertNotNull(order1.getId());
        assertEquals("Иванов", order1.getCustomerName());
        assertEquals("Ноутбук", order1.getProduct());
        assertEquals(10, order1.getQuantity());
        assertFalse(order1.isUrgent());
        assertNotNull(order1.getCreatedAt());
        assertEquals("CREATED", order1.getStatus());
    }

    @Test
    void testSetters() {
        Order order = Order.create("Тест", "Товар", 5, false);

        order.setStatus("PROCESSED");
        assertEquals("PROCESSED", order.getStatus());

        order.setProcessedAt(java.time.LocalDateTime.now());
        assertNotNull(order.getProcessedAt());
    }

    @Test
    void testUrgentOrder() {
        Order urgent = Order.create("Срочный", "Товар", 1, true);
        assertTrue(urgent.isUrgent());
    }

    @Test
    void testUniqueIds() {
        Order orderA = Order.create("A", "Product", 1, false);
        Order orderB = Order.create("B", "Product", 1, false);
        assertNotEquals(orderA.getId(), orderB.getId());
    }

    @Test
    void testInitialStatus() {
        Order order = Order.create("Тест", "Товар", 5, false);
        assertEquals("CREATED", order.getStatus());
    }
}