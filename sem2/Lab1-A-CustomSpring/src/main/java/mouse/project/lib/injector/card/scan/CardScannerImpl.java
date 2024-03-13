package mouse.project.lib.injector.card.scan;

import mouse.project.lib.annotation.Auto;
import mouse.project.lib.exception.CardException;
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

public class CardScannerImpl implements CardScanner {
    @Override
    public <T> CardDefinition<T> scan(Class<T> tClass) {
        DefinedCard<T> cardDefinition = new DefinedCardImpl<>(tClass);
        for (Scanner scanner : scannerList) {
            scanner.scan(cardDefinition, tClass);
        }
        return cardDefinition;
    }
    private final List<Scanner> scannerList;
    public CardScannerImpl() {
        DefinitionHelper definitionHelper = new DefinitionHelperImpl();
        scannerList = new ArrayList<>();
        scannerList.add(new ConstructorScanner(definitionHelper));
        scannerList.add(new MethodScanner(definitionHelper));
        scannerList.add(new FieldScanner(definitionHelper));
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
    private interface Scanner {
        <T> void scan(DefinedCard<T> producer, Class<T> toScan);
    }
    private static class ConstructorScanner implements Scanner {
        private final DefinitionHelper definitionHelper;
        public ConstructorScanner(DefinitionHelper definitionHelper) {
            this.definitionHelper = definitionHelper;
        }

        @Override
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

    private static class FieldScanner implements Scanner {
        private final DefinitionHelper definitionHelper;
        public FieldScanner(DefinitionHelper definitionHelper) {
            this.definitionHelper = definitionHelper;
        }

        @Override
        public <T> void scan(DefinedCard<T> card, Class<T> toScan) {
            List<Field> annotatedFields = getAnnotatedFields(toScan);
            for (Field field : annotatedFields) {
                FieldDefinition fieldDef = definitionHelper.getField(field);
                card.addField(fieldDef);
            }
        }

        private <T> List<Field> getAnnotatedFields(Class<T> clazz) {
            Field[] fields = clazz.getFields();
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
    private static class MethodScanner implements Scanner {
        private final DefinitionHelper definitionHelper;
        public MethodScanner(DefinitionHelper definitionHelper) {
            this.definitionHelper = definitionHelper;
        }

        @Override
        public <T> void scan(DefinedCard<T> card, Class<T> toScan) {
            List<Method> annotatedFields = getAnnotatedSetters(toScan);
            for (Method method : annotatedFields) {
                SetterDefinition setter = definitionHelper.getSetter(method);
                card.addSetter(setter);
            }
        }

        private <T> List<Method> getAnnotatedSetters(Class<T> clazz) {
            Method[] methods = clazz.getMethods();
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
