package mouse.project.lib.web.factory;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.annotation.Param;
import mouse.project.lib.web.factory.translations.ParamTranslationFactory;
import mouse.project.lib.web.invoker.ControllerInvoker;
import mouse.project.lib.web.invoker.ParamTranslation;
import mouse.project.lib.web.invoker.ParameterDesc;
import mouse.project.lib.web.invoker.ParameterDescImpl;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ControllerInvokerFactoryImpl implements ControllerInvokerFactory {
    @Auto
    private ParamTranslationFactory paramTranslationFactory;
    @Override
    public ControllerInvoker create(Object controller, Method method) {
        Parameter[] parameters = method.getParameters();
        List<ParameterDesc> paramList = new ArrayList<>();
        for (Parameter parameter : parameters) {
            ParameterDesc parameterDesc = processParameter(parameter);
            paramList.add(parameterDesc);
        }

    }

    private ParameterDesc processParameter(Parameter parameter) {
        String name = getParameterName(parameter);
        String defaultValue = getParameterDefault(parameter);
        Class<?> type = parameter.getType();
        ParamTranslation translation = createParamTranslation(type);
        return new ParameterDescImpl(name, defaultValue, type, translation);
    }

    private String getParameterDefault(Parameter parameter) {

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
