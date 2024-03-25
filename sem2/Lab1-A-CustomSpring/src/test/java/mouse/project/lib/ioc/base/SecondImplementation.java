package mouse.project.lib.ioc.base;

import mouse.project.lib.annotation.Order;
import mouse.project.lib.annotation.Service;

@Service
@Order(2)
public class SecondImplementation implements ServiceInterface {
    @Override
    public String getString() {
        return "Second";
    }
}
