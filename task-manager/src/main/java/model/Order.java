package model;

import annotations.*;
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
    @NotNull(message = "ID не может быть null")
    private Long id;

    @NotNull(message = "Имя клиента не может быть null")
    @NotEmpty(message = "Имя клиента не может быть пустым")
    private String customerName;

    @NotNull(message = "Товар не может быть null")
    @NotEmpty(message = "Товар не может быть пустым")
    private String product;

    @Min(1)
    @Max(100)
    private int quantity;

    @OrderType("REGULAR")
    @Builder.Default
    private String orderType = "REGULAR";

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
                .orderType(isUrgent ? "URGENT" : "REGULAR")
                .status("CREATED")
                .build();
    }

    public boolean isUrgent() {
        return "URGENT".equals(orderType);
    }
}