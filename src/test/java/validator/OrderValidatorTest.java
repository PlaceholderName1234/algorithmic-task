package validator;

import model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {

    private Order validOrder;

    @BeforeEach
    void setUp() {
        validOrder = Order.create("Иванов", "Ноутбук", 10, false);
    }

    @Test
    void testValidOrder() {
        List<String> errors = OrderValidator.validate(validOrder);
        assertTrue(errors.isEmpty());
        assertTrue(OrderValidator.isValid(validOrder));
    }

    @Test
    void testInvalidCustomerName() {
        Order order = Order.create(null, "Ноутбук", 10, false);
        List<String> errors = OrderValidator.validate(order);
        assertFalse(errors.isEmpty());
    }

    @Test
    void testEmptyCustomerName() {
        Order order = Order.create("", "Ноутбук", 10, false);
        List<String> errors = OrderValidator.validate(order);
        assertFalse(errors.isEmpty());
    }

    @Test
    void testInvalidProduct() {
        Order order = Order.create("Иванов", null, 10, false);
        List<String> errors = OrderValidator.validate(order);
        assertFalse(errors.isEmpty());
    }

    @Test
    void testEmptyProduct() {
        Order order = Order.create("Иванов", "", 10, false);
        List<String> errors = OrderValidator.validate(order);
        assertFalse(errors.isEmpty());
    }

    @Test
    void testQuantityTooLow() {
        Order order = Order.create("Иванов", "Ноутбук", 0, false);
        List<String> errors = OrderValidator.validate(order);
        assertFalse(errors.isEmpty());
    }

    @Test
    void testQuantityTooHigh() {
        Order order = Order.create("Иванов", "Ноутбук", 10000, false);
        List<String> errors = OrderValidator.validate(order);
        assertFalse(errors.isEmpty());
    }
}