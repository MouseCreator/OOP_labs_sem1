package mouse.project.lib.ioc.base;

import mouse.project.lib.annotation.Factory;
import mouse.project.lib.annotation.Primary;
import mouse.project.lib.annotation.Service;

@Service
public class ServiceFactory {
    @Primary
    @Factory
    public ServiceInterface getPrimaryService() {
        return () -> "First";
    }
}
