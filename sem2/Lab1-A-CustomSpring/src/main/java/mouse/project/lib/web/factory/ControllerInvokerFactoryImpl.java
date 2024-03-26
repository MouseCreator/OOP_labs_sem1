package mouse.project.lib.web.factory;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.factory.translations.ParamTranslationFactory;
import mouse.project.lib.web.invoker.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

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
        return null;
    }
}
