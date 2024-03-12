package mouse.project.lib.injector.sources.factory;

import mouse.project.lib.exception.PreparedActionException;
import mouse.project.lib.injector.sources.RequiredClass;
import mouse.project.lib.injector.sources.constructor.ConstructorHelper;
import mouse.project.lib.ioc.IocContainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class PreparedMethodCall implements PreparedAction {
    private final Method method;
    private final Object invokeFrom;
    public ConstructorHelper constructorHelper;

    public PreparedMethodCall(Method method, Object invokeFrom) {
        this.method = method;
        this.invokeFrom = invokeFrom;
        constructorHelper = ConstructorHelper.get();
    }

    @Override
    public Object call(IocContainer iocContainer) {
        method.setAccessible(true);
        Parameter[] parameters = method.getParameters();
        List<RequiredClass> requiredClasses = constructorHelper.inspectParameters(parameters);
        Object[] args = new Object[method.getParameterCount()];
        for (int i = 0; i < args.length; i++) {
            RequiredClass requiredClass = requiredClasses.get(i);
            Class<?> clazz = requiredClass.getRequiredClass();
            Object param;
            if (requiredClass.hasNamed()) {
                param = iocContainer.getNamed(clazz, requiredClass.getNamed());
            } else {
                param = iocContainer.getSingle(clazz);
            }
            args[i] = param;
        }
        try {
            return method.invoke(invokeFrom, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PreparedActionException(e);
        }
    }
}
