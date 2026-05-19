package model;

import annotations.OrderType;
import annotations.Validate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Order {
    private static final AtomicLong idGenerator = new AtomicLong(1);

    @EqualsAndHashCode.Include
    @Validate(notNull = true, message = "ID не может быть null")
    private Long id;

    @Validate(notNull = true, notEmpty = true, message = "Имя клиента не может быть пустым")
    private String customerName;

    @Validate(notNull = true, notEmpty = true, message = "Товар не может быть пустым")
    private String product;

    @Validate(min = 1, max = 100, message = "Количество должно быть от 1 до 100")
    private int quantity;

    @OrderType(urgent = false)
    @Builder.Default
    private boolean isUrgent = false;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime processedAt;
    private String status;

    public static Order create(String customerName, String product, int quantity, boolean isUrgent) {
        return Order.builder()
                .id(idGenerator.getAndIncrement())
                .customerName(customerName)
                .product(product)
                .quantity(quantity)
                .isUrgent(isUrgent)
                .status("CREATED")
                .build();
    }
}