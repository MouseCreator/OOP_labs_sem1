package mouse.project.lib.ioc.base;

import mouse.project.lib.annotation.Factory;
import mouse.project.lib.annotation.Order;
import mouse.project.lib.annotation.Primary;
import mouse.project.lib.annotation.Service;

@Service
public class ServiceFactory {
    @Primary
    @Factory
    @Order(1)
    public ServiceInterface getPrimaryService() {
        return () -> "First";
    }
}
