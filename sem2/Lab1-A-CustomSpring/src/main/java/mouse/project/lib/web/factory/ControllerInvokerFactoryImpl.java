package mouse.project.lib.web.factory;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.annotation.DefaultValue;
import mouse.project.lib.web.annotation.Param;
import mouse.project.lib.web.factory.translations.ParamTranslationFactory;
import mouse.project.lib.web.invoker.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ControllerInvokerFactoryImpl implements ControllerInvokerFactory {

    private final ParamTranslationFactory paramTranslationFactory;
    @Auto
    public ControllerInvokerFactoryImpl(ParamTranslationFactory paramTranslationFactory) {
        this.paramTranslationFactory = paramTranslationFactory;
    }

    @Override
    public ControllerInvoker create(Object controller, Method method) {
        Parameter[] parameters = method.getParameters();
        List<ParameterDesc> paramList = new ArrayList<>();
        for (Parameter parameter : parameters) {
            ParameterDesc parameterDesc = processParameter(parameter);
            paramList.add(parameterDesc);
        }
        return new ControllerInvokerImpl(controller, method, paramList);
    }

    private ParameterDesc processParameter(Parameter parameter) {
        String name = getParameterName(parameter);
        String defaultValue = getParameterDefault(parameter);
        Class<?> type = parameter.getType();
        ParamTranslation translation = createParamTranslation(type);
        return new ParameterDescImpl(name, defaultValue, type, translation);
    }

    private String getParameterDefault(Parameter parameter) {
        DefaultValue annotation = parameter.getAnnotation(DefaultValue.class);
        if (annotation == null) {
            return null;
        }
        return annotation.value();
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
