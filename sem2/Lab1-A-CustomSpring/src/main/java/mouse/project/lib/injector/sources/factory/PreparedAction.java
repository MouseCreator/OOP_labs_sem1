package mouse.project.lib.injector.sources.factory;

import mouse.project.lib.ioc.IocContainer;

public interface PreparedAction {
    Object call(IocContainer iocContainer);
}
