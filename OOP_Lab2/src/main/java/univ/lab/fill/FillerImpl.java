package univ.lab.fill;

import java.lang.reflect.Field;

public class FillerImpl implements Filler {
    @Override
    public <T> void fill(Object toInitialize, String attribute, T value) {
        Field[] declaredFields = toInitialize.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Fill.class)) {
                Fill annotation = field.getAnnotation(Fill.class);
                if (annotation.attribute().equals(attribute)) {
                    setField(field, toInitialize, value);
                }
            }
        }
    }

    private <T> void setField(Field field, Object obj, T value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
           throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Cannot set field value. Expected: " + field.getType().getName() + ", but got " + value.getClass().getName(), e);
        }
    }
}
