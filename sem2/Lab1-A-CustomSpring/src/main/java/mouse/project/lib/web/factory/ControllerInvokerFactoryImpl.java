package mouse.project.lib.web.factory;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.annotation.DefaultValue;
import mouse.project.lib.web.annotation.Param;
import mouse.project.lib.web.annotation.RBody;
import mouse.project.lib.web.factory.translations.ParamTranslationFactory;
import mouse.project.lib.web.invoker.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ControllerInvokerFactoryImpl implements ControllerInvokerFactory {

    private final ParamTranslationFactory paramTranslationFactory;
    private final BodyProcessor bodyProcessor;
    @Auto
    public ControllerInvokerFactoryImpl(ParamTranslationFactory paramTranslationFactory,
                                        BodyProcessor bodyProcessor) {
        this.paramTranslationFactory = paramTranslationFactory;
        this.bodyProcessor = bodyProcessor;
    }

    @Override
    public ControllerInvoker create(Object controller, Method method) {
        Parameter[] parameters = method.getParameters();
        List<ParameterDesc> paramList = new ArrayList<>();
        for (Parameter parameter : parameters) {
            ParameterDesc parameterDesc = processParameter(parameter);
            paramList.add(parameterDesc);
        }
        return new ControllerInvokerImpl(controller, method, paramList, bodyProcessor);
    }

    private ParameterDesc processParameter(Parameter parameter) {
        if (parameter.isAnnotationPresent(RBody.class)) {
            RBody annotation = parameter.getAnnotation(RBody.class);
            String attribute = annotation.value();
            Class<?> type = parameter.getType();
            return new ParameterDescImpl(attribute, "", type, true);
        } else {
            return createFromRequestParameter(parameter);
        }

    }

    private ParameterDescImpl createFromRequestParameter(Parameter parameter) {
        String name = getParameterName(parameter);
        String defaultValue = getParameterDefault(parameter);
        Class<?> type = parameter.getType();
        return new ParameterDescImpl(name, defaultValue, type, false);
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
