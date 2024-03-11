package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.annotation.UseNamed;
import mouse.project.lib.injector.sources.RequiredClass;

import java.lang.reflect.Parameter;

public class ConstructorHelper {
    private RequiredClass inspectParameter(Parameter parameter) {
        UseNamed annotation = parameter.getAnnotation(UseNamed.class);
        String named = annotation == null ? null : annotation.named();
        Class<?> requiredType = parameter.getType();
        return new RequiredClass(requiredType, named);
    }

    private ConstructorHelper() {

    }
    private static ConstructorHelper instance = null;

    public static ConstructorHelper get(){
        if (instance == null) {
            instance = new ConstructorHelper();
        }
        return instance;
    }
}
