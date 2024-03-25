package mouse.project.lib.ioc;

import mouse.project.lib.ioc.base.ConfigClass;
import mouse.project.lib.ioc.base.ServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Test
    void testOrdering() {
        Inj inj = getMainConfig();
        Collection<ServiceInterface> collection = inj.getAll(ServiceInterface.class);
        assertNotNull(collection);
        List<ServiceInterface> list = new ArrayList<>(collection);
        System.out.println(list);
        assertEquals(2, list.size());
        assertEquals("First", list.get(0).getString());
        assertEquals("Second", list.get(1).getString());
    }
}