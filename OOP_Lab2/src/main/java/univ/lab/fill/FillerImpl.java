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
                    setPrimitiveOrObject(field, toInitialize, value);
                }
            }
        }
    }
    private <T> void setPrimitiveOrObject(Field field, Object toInitialize, T value) {
        if (value instanceof String) {
            setPrimitive(field, toInitialize, value);
        } else {
            setField(field, toInitialize, value);
        }
    }
    private <T> void setField(Field field, Object obj, T value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
           throw new RuntimeException("Field is not accessible", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Cannot set field value. Expected: "
                    + field.getType().getName() + ", but got " + value.getClass().getName(), e);
        }
    }

    private <T> void setPrimitive(Field field, Object toInitialize, T value) {
        String stringValue = (String) value;
        Class<?> fieldType = field.getType();
        if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
            setField(field, toInitialize, Boolean.parseBoolean(stringValue));
        } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
            setField(field, toInitialize, Integer.parseInt(stringValue));
        } else if (fieldType.equals(String.class)) {
            setField(field, toInitialize, stringValue);
        } else {
            throw new UnsupportedOperationException("Unsupported type to cast from String:" + value.getClass().getName());
        }
    }
}
