package mouse.project.lib.web.factory;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.annotation.Param;
import mouse.project.lib.web.factory.translations.ParamTranslationFactory;
import mouse.project.lib.web.invoker.ControllerInvoker;
import mouse.project.lib.web.invoker.ParamTranslation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Service
public class ControllerInvokerFactoryImpl implements ControllerInvokerFactory {
    @Auto
    private ParamTranslationFactory paramTranslationFactory;
    @Override
    public ControllerInvoker create(Object controller, Method method) {
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            processParameter(parameter);
        }
    }

    private String processParameter(Parameter parameter) {
        String name = getParameterName(parameter);
        Class<?> type = parameter.getType();
        ParamTranslation translation = createParamTranslation(type);
    }

    private ParamTranslation createParamTranslation(Class<?> type) {
        return paramTranslationFactory.create(type);
    }

    private static String getParameterName(Parameter parameter) {
        String parameterName;
        Param annotation = parameter.getAnnotation(Param.class);
        if (annotation != null) {
            parameterName = annotation.value();
        } else {
            parameterName = parameter.getName();
        }
        return parameterName;
    }
}
