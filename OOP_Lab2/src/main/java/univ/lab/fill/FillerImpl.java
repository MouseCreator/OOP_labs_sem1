package univ.lab.fill;

import java.lang.reflect.*;
import java.util.List;

public class FillerImpl implements Filler {

    @Override
    public void fill(Object toInitialize, String attribute, Object value) {
        Field[] declaredFields = toInitialize.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Fill.class)) {
                Fill annotation = field.getAnnotation(Fill.class);
                if (annotation.attribute().equals(attribute)) {
                    setPrimitiveOrObject(field, toInitialize, value);
                }
            }
            else if (field.isAnnotationPresent(FillList.class)) {
                FillList annotation = field.getAnnotation(FillList.class);
                if (annotation.attribute().equals(attribute)) {
                    appendList(field, toInitialize, value);
                }
            }
        }
    }

    private String isListOf(Field field) {
        Type genericFieldType = field.getGenericType();

        if (genericFieldType instanceof ParameterizedType parameterizedType) {
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            for (Type typeArgument : typeArguments) {
                if (typeArgument.equals(Integer.class)) {
                    return "integer";
                }
                else if (typeArgument.equals(Boolean.class)) {
                    return "boolean";
                }
                else if (typeArgument.equals(String.class)) {
                    return "string";
                }
            }
        }
        throw new UnsupportedOperationException("Unsupported type to cast from String for Field:" + field.getName());
    }
    private void setPrimitiveOrObject(Field field, Object toInitialize, Object value) {
        if (value instanceof String) {
            setPrimitive(field, toInitialize, value);
        } else {
            setField(field, toInitialize, value);
        }
    }
    private void setField(Field field, Object obj, Object value) {
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
    private void appendList(Field field, Object toInit, Object toAdd) {
        if (toAdd instanceof String str) {
            String listOf = isListOf(field);
            switch (listOf) {
                case "integer" -> toAdd = Integer.parseInt(str);
                case "boolean" -> toAdd = Boolean.parseBoolean(str);
            }
        }
        try {
            if (field.get(toAdd) == null) {
                initList(field, toInit, toAdd);
            } else {
                addToList(field, toAdd, toInit);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void initList(Field field, Object toInit, Object t) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object list = field.getType().getConstructor().newInstance();
        Method add = List.class.getDeclaredMethod("add",Object.class);
        add.invoke(list, t);
        field.set(toInit, list);
    }

    private void addToList(Field field, Object obj, Object t) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object list = field.get(obj);
        Method add = List.class.getDeclaredMethod("add",Object.class);
        add.invoke(list, t);
    }

    private void setPrimitive(Field field, Object toInitialize, Object value) {
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
