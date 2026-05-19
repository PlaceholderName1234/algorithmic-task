package validator;

import annotations.Max;
import annotations.Min;
import annotations.NotEmpty;
import annotations.NotNull;
import model.Order;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OrderValidator {

    public static List<String> validate(Order order) {
        List<String> errors = new ArrayList<>();

        for (Field field : Order.class.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(order);

                if (field.isAnnotationPresent(NotNull.class)) {
                    NotNull notNull = field.getAnnotation(NotNull.class);
                    if (value == null) {
                        errors.add(notNull.message());
                    }
                }

                if (field.isAnnotationPresent(NotEmpty.class) && value instanceof String strValue) {
                    NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
                    if (strValue.trim().isEmpty()) {
                        errors.add(notEmpty.message());
                    }
                }

                if (field.isAnnotationPresent(Min.class) && value instanceof Number) {
                    Min min = field.getAnnotation(Min.class);
                    int intValue = ((Number) value).intValue();
                    if (intValue < min.value()) {
                        String message = min.message().isEmpty()
                                ? "Поле " + field.getName() + " должно быть не меньше " + min.value()
                                : min.message();
                        errors.add(message);
                    }
                }

                if (field.isAnnotationPresent(Max.class) && value instanceof Number) {
                    Max max = field.getAnnotation(Max.class);
                    int intValue = ((Number) value).intValue();
                    if (intValue > max.value()) {
                        String message = max.message().isEmpty()
                                ? "Поле " + field.getName() + " должно быть не больше " + max.value()
                                : max.message();
                        errors.add(message);
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add("Ошибка валидации поля " + field.getName());
            }
        }

        return errors;
    }

    public static boolean isValid(Order order) {
        return validate(order).isEmpty();
    }
}