package mouse.project.lib.injector.card.scan;

import mouse.project.lib.annotation.Auto;
import mouse.project.lib.exception.CardException;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.container.Implementations;
import mouse.project.lib.injector.card.definition.*;
import mouse.project.lib.injector.card.helper.DefinitionHelper;
import mouse.project.lib.injector.card.helper.DefinitionHelperImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefinedCardScanner implements CardScanner {

    private final ConstructorScanner constructorScanner;
    private final MethodScanner methodScanner;
    private final FieldScanner fieldScanner;
    public DefinedCardScanner() {
        DefinitionHelper definitionHelper = new DefinitionHelperImpl();
        constructorScanner = new ConstructorScanner(definitionHelper);
        methodScanner = new MethodScanner(definitionHelper);
        fieldScanner = new FieldScanner(definitionHelper);
    }
    @Override
    public <T> CardDefinition<T> scan(Class<T> tClass) {
        validateCanBeProduced(tClass);
        Implementation<T> implementation = Implementations.create(tClass);
        DefinedCard<T> cardDefinition = new DefinedCardImpl<>(implementation);
        scanGivenClass(cardDefinition, tClass);
        scanSuperClasses(cardDefinition, tClass);
        return cardDefinition;
    }

    private <T> void scanGivenClass(DefinedCard<T> cardDefinition, Class<T> tClass) {
        constructorScanner.scan(cardDefinition, tClass);
        methodScanner.scan(cardDefinition, tClass);
        fieldScanner.scan(cardDefinition, tClass);
    }

    private <T> void scanSuperClasses(DefinedCard<T> definedCard, Class<?> tClass) {
        while (tClass != Object.class) {
            tClass = tClass.getSuperclass();
            methodScanner.scan(definedCard, tClass);
            fieldScanner.scan(definedCard, tClass);
        }
    }

    private void validateCanBeProduced(Class<?> clazz) {
        if(Modifier.isAbstract(clazz.getModifiers())) {
            throw new CardException("Cannot produce an abstract class: " + clazz);
        }
        if (clazz.isInterface()) {
            throw new CardException("Cannot produce an interface: " + clazz);
        }
        if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
            throw new CardException("Cannot produce inner non-static class:" + clazz);
        }
    }

    private record ConstructorScanner(DefinitionHelper definitionHelper) {
        public <T> void scan(DefinedCard<T> card, Class<T> toScan) {
            Optional<Constructor<T>> annotated = getAnnotatedConstructor(toScan);
            if (annotated.isPresent()) {
                Constructor<T> classConstructor = annotated.get();
                ConstructorDefinition<T> constructor = definitionHelper.getConstructor(classConstructor);
                card.setPrimaryConstructor(constructor);
                return;
            }
            Optional<Constructor<T>> noArgsConstructor = getNoArgsConstructor(toScan);
            if (noArgsConstructor.isPresent()) {
                ConstructorDefinition<T> constructor = definitionHelper.getConstructor(noArgsConstructor.get());
                card.setPrimaryConstructor(constructor);
                return;
            }
            throw new CardException("Neither @Auto, nor no-args constructor found for class: " + toScan);
        }

        private <T> Optional<Constructor<T>> getAnnotatedConstructor(Class<T> clazz) {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            Constructor<T> result = null;
            for (Constructor<?> constructor : constructors) {
                Auto annotation = constructor.getAnnotation(Auto.class);
                if (annotation == null) {
                    continue;
                }
                if (result != null) {
                    throw new CardException("More than one @Auto-constructor found for: " + clazz);
                }
                result = toConstructor(clazz, constructor);
            }
            return Optional.ofNullable(result);
        }

        private <T> Optional<Constructor<T>> getNoArgsConstructor(Class<T> clazz) {
            try {
                Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
                return Optional.of(declaredConstructor);
            } catch (NoSuchMethodException e) {
                return Optional.empty();
            }
        }

        private <T> Constructor<T> toConstructor(Class<T> clazz, Constructor<?> constructor) {
            Constructor<T> result;
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            try {
                result = clazz.getConstructor(parameterTypes);
            } catch (NoSuchMethodException e) {
                throw new CardException(e);
            }
            return result;
        }
    }

    private record FieldScanner(DefinitionHelper definitionHelper) {

        public <T> void scan(DefinedCard<T> card, Class<?> toScan) {
            List<Field> annotatedFields = getAnnotatedFields(toScan);
            for (Field field : annotatedFields) {
                FieldDefinition fieldDef = definitionHelper.getField(field);
                card.addField(fieldDef);
            }
        }

        private List<Field> getAnnotatedFields(Class<?> clazz) {
            Field[] fields = clazz.getDeclaredFields();
            List<Field> result = new ArrayList<>();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Auto.class)) {
                    continue;
                }
                result.add(field);
            }
            return result;
        }
    }

    private record MethodScanner(DefinitionHelper definitionHelper) {

        public <T> void scan(DefinedCard<T> card, Class<?> toScan) {
            List<Method> annotatedFields = getAnnotatedSetters(toScan);
            for (Method method : annotatedFields) {
                SetterDefinition setter = definitionHelper.getSetter(method);
                card.addSetter(setter);
            }
        }

        private <T> List<Method> getAnnotatedSetters(Class<T> clazz) {
            Method[] methods = clazz.getDeclaredMethods();
            List<Method> result = new ArrayList<>();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(Auto.class)) {
                    continue;
                }
                result.add(method);
            }
            return result;
        }
    }
}
