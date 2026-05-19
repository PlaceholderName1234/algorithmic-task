package validator;

import annotations.Validate;
import model.Order;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OrderValidator {

    public static List<String> validate(Order order) {
        List<String> errors = new ArrayList<>();

        for (Field field : Order.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Validate.class)) {
                Validate validate = field.getAnnotation(Validate.class);
                field.setAccessible(true);

                try {
                    Object value = field.get(order);

                    if (validate.notNull() && value == null) {
                        String message = validate.message().isEmpty()
                                ? "Поле " + field.getName() + " не может быть null"
                                : validate.message();
                        errors.add(message);
                    }

                    if (validate.notEmpty() && value instanceof String strValue && strValue.trim().isEmpty()) {
                        String message = validate.message().isEmpty()
                                ? "Поле " + field.getName() + " не может быть пустым"
                                : validate.message();
                        errors.add(message);
                    }

                    if (value instanceof Number numberValue) {
                        int intValue = numberValue.intValue();
                        if (intValue < validate.min()) {
                            errors.add("Поле " + field.getName() + " должно быть не меньше " + validate.min());
                        }
                        if (intValue > validate.max()) {
                            errors.add("Поле " + field.getName() + " должно быть не больше " + validate.max());
                        }
                    }

                } catch (IllegalAccessException e) {
                    errors.add("Ошибка валидации поля " + field.getName());
                }
            }
        }

        return errors;
    }

    public static boolean isValid(Order order) {
        return validate(order).isEmpty();
    }
}