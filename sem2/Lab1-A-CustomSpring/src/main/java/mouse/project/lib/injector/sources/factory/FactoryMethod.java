package mouse.project.lib.injector.sources.factory;

import mouse.project.lib.ioc.IocContainer;
public class FactoryMethod implements Factory{
    private PreparedMethodCall preparedMethodCall;
    private IocContainer iocContainer;
    private Class<?> provides;
    @Override
    public Class<?> forClass() {
        return provides;
    }

    @Override
    public Object create() {
        return preparedMethodCall.call(iocContainer);
    }
}
