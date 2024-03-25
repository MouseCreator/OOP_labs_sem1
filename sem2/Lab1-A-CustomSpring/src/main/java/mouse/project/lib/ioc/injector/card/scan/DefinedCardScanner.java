package mouse.project.lib.ioc.injector.card.scan;

import mouse.project.lib.annotation.After;
import mouse.project.lib.annotation.Auto;
import mouse.project.lib.annotation.Factory;
import mouse.project.lib.exception.CardException;
import mouse.project.lib.ioc.injector.TypeValidator;
import mouse.project.lib.ioc.injector.card.container.Implementation;
import mouse.project.lib.ioc.injector.card.container.Implementations;
import mouse.project.lib.ioc.injector.card.definition.*;
import mouse.project.lib.ioc.injector.card.helper.DefinitionHelper;
import mouse.project.lib.ioc.injector.card.helper.DefinitionHelperImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefinedCardScanner implements CardScanner {

    private final ConstructorScanner constructorScanner;
    private final List<GenericScanner> genericScanners = new ArrayList<>();
    public DefinedCardScanner() {
        DefinitionHelper definitionHelper = new DefinitionHelperImpl();
        constructorScanner = new ConstructorScanner(definitionHelper);
        genericScanners.add(new MethodScanner(definitionHelper));
        genericScanners.add(new FieldScanner(definitionHelper));
        genericScanners.add(new FactoryScanner(definitionHelper));
        genericScanners.add(new ActionScanner(definitionHelper));
    }
    @Override
    public <T> DefinedCard<T> scan(Class<T> tClass) {
        TypeValidator typeValidator = new TypeValidator();
        typeValidator.validateCanBeProduced(tClass);
        Implementation<T> implementation = Implementations.create(tClass);
        DefinedCard<T> cardDefinition = new DefinedCardImpl<>(implementation);
        scanGivenClass(cardDefinition, tClass);
        scanSuperClasses(cardDefinition, tClass);
        return cardDefinition;
    }

    private <T> void scanGivenClass(DefinedCard<T> cardDefinition, Class<T> tClass) {
        constructorScanner.scan(cardDefinition, tClass);
        genericScanners.forEach(s -> s.scan(cardDefinition, tClass));
    }

    private <T> void scanSuperClasses(DefinedCard<T> definedCard, Class<?> tClass) {
        while (tClass != Object.class) {
            tClass = tClass.getSuperclass();
            final Class<?> current = tClass;
            genericScanners.forEach(s -> s.scan(definedCard, current));
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
    private interface GenericScanner {
        <T> void scan(DefinedCard<T> card, Class<?> toScan);
    }
    private record FieldScanner(DefinitionHelper definitionHelper) implements GenericScanner {

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

    private record MethodScanner(DefinitionHelper definitionHelper) implements GenericScanner {

        public <T> void scan(DefinedCard<T> card, Class<?> toScan) {
            List<Method> methods = getAnnotatedSetters(toScan);
            for (Method method : methods) {
                SetterDefinition setter = definitionHelper.getSetter(method);
                card.addSetter(setter);
            }
        }

        private <T> List<Method> getAnnotatedSetters(Class<T> clazz) {
            return getAnnotatedMethod(clazz, Auto.class);
        }
    }
    private static <T> List<Method> getAnnotatedMethod(Class<T> clazz, Class<? extends Annotation> annotation) {
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> result = new ArrayList<>();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(annotation)) {
                continue;
            }
            result.add(method);
        }
        return result;
    }
    private record ActionScanner(DefinitionHelper definitionHelper) implements GenericScanner {

        public <T> void scan(DefinedCard<T> card, Class<?> toScan) {
            List<Method> methods = getActions(toScan);
            for (Method method : methods) {
                ActionDefinition actionDefinition = definitionHelper.getAction(method);
                card.addAction(actionDefinition);
            }
        }

        private <T> List<Method> getActions(Class<T> clazz) {
            return getAnnotatedMethod(clazz, After.class);
        }
    }

    private record FactoryScanner(DefinitionHelper definitionHelper) implements GenericScanner {

        public <T> void scan(DefinedCard<T> card, Class<?> toScan) {
            List<Method> methods = getFactories(toScan);
            for (Method method : methods) {
                MethodDefinition factoryMethod = definitionHelper.getFactoryMethod(method, card.getType());
                card.addFactoryDefinition(new FactoryCardImpl<>(factoryMethod, Implementations.create(method)));
            }
        }

        private <T> List<Method> getFactories(Class<T> clazz) {
            return getAnnotatedMethod(clazz, Factory.class);
        }
    }
}
