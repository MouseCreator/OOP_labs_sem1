package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.annotation.UseNamed;
import mouse.project.lib.injector.sources.RequiredClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ConstructorHelper {


    private ConstructorHelper() {
    }
    private static ConstructorHelper instance = null;

    public static ConstructorHelper get(){
        if (instance == null) {
            instance = new ConstructorHelper();
        }
        return instance;
    }

    public RequiredClass inspectField(Field field) {
        Class<?> requiredType = field.getType();
        UseNamed useNamed = field.getAnnotation(UseNamed.class);
        String named = useNamed == null ? null : useNamed.named();
        return new RequiredClass(requiredType, named);
    }

    public RequiredClass inspectParameter(Parameter parameter) {
        UseNamed annotation = parameter.getAnnotation(UseNamed.class);
        String named = annotation == null ? null : annotation.named();
        Class<?> requiredType = parameter.getType();
        return new RequiredClass(requiredType, named);
    }

    public List<RequiredClass> inspectParameters(Parameter[] parameters) {
        List<RequiredClass> result = new ArrayList<>();
        for (Parameter parameter : parameters) {
            RequiredClass requiredClass = inspectParameter(parameter);
            result.add(requiredClass);
        }
        return result;
    }
}
