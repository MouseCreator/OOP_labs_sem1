package mouse.project.lib.ioc;

import mouse.project.lib.ioc.base.ConfigClass;
import mouse.project.lib.ioc.base.ServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IocTest {
    private Inj getMainConfig() {
        return Ioc.getConfiguredInjector(ConfigClass.class);
    }

    @Test
    void testPrimaryAnnotation() {
        Inj inj = getMainConfig();
        ServiceInterface serviceInterface = inj.get(ServiceInterface.class);
        assertNotNull(serviceInterface);
        String string = serviceInterface.getString();
        assertEquals("First", string);
    }
}