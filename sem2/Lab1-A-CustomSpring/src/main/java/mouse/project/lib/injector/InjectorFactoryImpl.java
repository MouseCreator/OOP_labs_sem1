package mouse.project.lib.injector;

import mouse.project.lib.annotation.Configuration;
import mouse.project.lib.annotation.Factory;
import mouse.project.lib.annotation.UseNamed;
import mouse.project.lib.injector.sources.ClassDescriptor;
import mouse.project.lib.injector.sources.RequiredClass;
import mouse.project.lib.utils.Utils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class InjectorFactoryImpl implements InjectorFactory {
    @Override
    public Injector createFromConfiguration(Class<?> configClass) {
        return createNewConfiguration(configClass);
    }

    private Injector createNewConfiguration(Class<?> configurationClass) {
        Configuration configuration = Utils.getAnnotation(configurationClass, Configuration.class);
        String basePackage = configuration.basePackage();
        Class<?>[] loadHelpers = configuration.loadFrom();
        return null;
    }
    private Set<Class<?>> scanBasePackage(String packageName) {
        AnnotationManager annotationManager = new AnnotationManagerImpl();
        List<Class<? extends Annotation>> targetAnnotations = annotationManager.getTargetAnnotations();
        return scanBasePackageFor(packageName, targetAnnotations);
    }
    private Set<Class<?>> scanBasePackageFor(String packageName,
                                             Collection<Class<? extends Annotation>> annotationClasses) {
        Reflections reflections = new Reflections(packageName, Scanners.TypesAnnotated);

        Set<Class<?>> annotatedClasses = new HashSet<>();
        for (Class<? extends Annotation> annotationClass : annotationClasses) {
            annotatedClasses.addAll(reflections.getTypesAnnotatedWith(annotationClass));
        }

        return annotatedClasses;
    }
    private void scanConfigurationClass(Class<?> configClass) {
        List<ClassDescriptor> descriptors = lookForFactories(configClass);
    }

    private List<ClassDescriptor> lookForFactories(Class<?> clazz) {
        List<ClassDescriptor> descriptors = new ArrayList<>();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Factory.class)) {
                ClassDescriptor descriptor = createDescriptorFromMethod(method);
                descriptors.add(descriptor);
            }
        }
        return descriptors;
    }

    private ClassDescriptor createDescriptorFromMethod(Method method) {
        method.setAccessible(true);
        Class<?> returnType = method.getReturnType();
        Parameter[] parameters = method.getParameters();
        return createFromParametersAndReturnType(parameters, returnType);

    }
    private <T> ClassDescriptor createDescriptorFromConstructor(Constructor<T> constructor, Class<T> clazz) {
        constructor.setAccessible(true);
        Parameter[] parameters = constructor.getParameters();
        return createFromParametersAndReturnType(parameters, clazz);

    }

    private ClassDescriptor createFromParametersAndReturnType(Parameter[] parameters, Class<?> returnType) {
        List<RequiredClass> requirements = new ArrayList<>();
        for (Parameter parameter : parameters) {
            RequiredClass requiredClass = inspectParameter(parameter);
            requirements.add(requiredClass);
        }
        return new ClassDescriptor(returnType, requirements);
    }

    private RequiredClass inspectParameter(Parameter parameter) {
        UseNamed annotation = parameter.getAnnotation(UseNamed.class);
        String named = annotation == null ? null : annotation.named();
        Class<?> requiredType = parameter.getType();
        return new RequiredClass(requiredType, named);
    }

    private void appendFromSetters(ClassDescriptor descriptor, List<Method> methods) {
        for (Method method : methods) {
            method.setAccessible(true);
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                RequiredClass requiredClass = inspectParameter(parameter);
                descriptor.addRequired(requiredClass);
            }
        }
    }

    private void appendFromFields(ClassDescriptor descriptor, List<Field> fields) {
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> requireType = field.getType();
            UseNamed annotation = field.getAnnotation(UseNamed.class);
            String named = annotation == null ? null : annotation.named();
            RequiredClass requiredClass = new RequiredClass(requireType, named);
            descriptor.addRequired(requiredClass);
        }
    }


}
