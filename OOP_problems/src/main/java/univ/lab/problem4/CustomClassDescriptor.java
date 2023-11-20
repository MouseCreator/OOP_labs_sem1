package univ.lab.problem4;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class CustomClassDescriptor {

    private final boolean parentFields;

    public CustomClassDescriptor(boolean parentFields) {
        this.parentFields = parentFields;
    }

    public CustomClassDescriptor() {
        this.parentFields = true;
    }

    public String describe(Class<?> inputClass) {
        StringBuilder builder = new StringBuilder();
        builder.append("Description of ").append(inputClass.getSimpleName()).append("\n");
        generalInfo(inputClass, builder);
        annotations(inputClass, builder);
        fields(inputClass, builder);
        methods(inputClass, builder);
        constructors(inputClass, builder);
        interfaces(inputClass, builder);
        members(inputClass, builder);
        extensions(inputClass, builder);
        return builder.toString();
    }

    private void constructors(Class<?> inputClass, StringBuilder builder) {
        builder.append("Constructors:\n");
        for (var constructor : inputClass.getConstructors()) {
            builder.append(constructor).append("\n");
        }
    }

    private <T> void listAll(StringBuilder builder, String name, T[] list) {
        builder.append(name).append(":\n");
        for (var t : list) {
            builder.append(t).append("\n");
        }
    }

    private void fields(Class<?> inputClass, StringBuilder builder) {
        listAll(builder, "Fields", inputClass.getDeclaredFields());
    }

    private void methods(Class<?> inputClass, StringBuilder builder) {
        listAll(builder, "Methods", inputClass.getMethods());
    }
    private void members(Class<?> inputClass, StringBuilder builder) {
        builder.append("Members:\n");
        for (var member : inputClass.getClasses()) {
            builder.append(member.getName()).append("\n");
        }
    }

    private void extensions(Class<?> inputClass, StringBuilder builder) {
        builder.append("Extensions: ");
        List<Class<?>> hierarchy = new ArrayList<>();
        Class<?> currentClass = inputClass;
        do {
            currentClass = currentClass.getSuperclass();
            hierarchy.add(currentClass);
        } while (!currentClass.equals(Object.class));
        printHierarchy(inputClass, builder, hierarchy);
        for (Class<?> classV : hierarchy) {
           describeParent(classV, builder);
        }
    }

    private void printHierarchy(Class<?> origin, StringBuilder builder, List<Class<?>> hierarchy) {
        builder.append(origin.getSimpleName());
        for (Class<?> classV : hierarchy) {
            builder.append("->").append(classV.getSimpleName());
        }
        builder.append("\n");
    }

    private void describeParent(Class<?> parent, StringBuilder builder) {
        add(builder, "Parent class", parent.getName());
        if (parentFields) {
            methods(parent, builder);
            fields(parent, builder);
            builder.append("_".repeat(20)).append("\n");
        } else {
            Method[] methods = parent.getDeclaredMethods();
            methodsStrict(builder, methods);
            fieldsStrict(parent, builder);
        }
    }

    private static void fieldsStrict(Class<?> parent, StringBuilder builder) {
        builder.append("Fields:\n");
        Field[] fields = parent.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                System.out.println(field);
            }
        }
    }

    private static void methodsStrict(StringBuilder builder, Method[] methods) {
        builder.append("Methods:\n");
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                System.out.println(method);
            }
        }
    }

    private void interfaces(Class<?> inputClass, StringBuilder builder) {
        listAll(builder, "Interfaces", inputClass.getInterfaces());
    }

    private void annotations(Class<?> inputClass, StringBuilder builder) {
        listAll(builder, "Annotations", inputClass.getAnnotations());
    }

    private void generalInfo(Class<?> inputClass, StringBuilder builder) {
        add(builder, "Name", inputClass.getName());
        add(builder, "Loader", inputClass.getClassLoader());
    }

    private void add(StringBuilder builder, String property, Object value) {
        builder.append(add(property, value));
        newLine(builder);
    }
    private void newLine(StringBuilder builder) {
        builder.append("\n");
    }
    private String add(String property, Object value) {
        return String.format(String.format("%s: %s", property, value == null ? "null" : value.toString()));
    }
}
