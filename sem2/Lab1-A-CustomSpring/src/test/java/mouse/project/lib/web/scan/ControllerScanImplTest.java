package mouse.project.lib.web.scan;

import mouse.project.lib.ioc.Inj;
import mouse.project.lib.ioc.Ioc;
import mouse.project.lib.ioc.annotation.Configuration;
import mouse.project.lib.ioc.annotation.Controller;
import mouse.project.lib.web.annotation.*;
import mouse.project.lib.web.exception.ControllerException;
import mouse.project.lib.web.factory.ControllerInvokerFactory;
import mouse.project.lib.web.register.ControllerInvoker;
import mouse.project.lib.web.register.RequestType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

class ControllerScanImplTest {
    @Configuration(basePackage = "mouse.project.lib.web.scan", includeClasses = MockCIF.class)
    private static class Config {

    }

    private static class MockCIF implements ControllerInvokerFactory {

        @Override
        public ControllerInvoker create(Object controller, Method method) {
            return null;
        }
    }
    private Inj getInj() {
        return Ioc.getConfiguredInjector(Config.class);
    }
    private ControllerScan getControllerScan() {
        return getInj().get(ControllerScan.class);
    }
    @Controller
    @RequestPrefix("home/")
    private static class ControllerSample {
        @Get
        @URL
        public void onGet() {
        }
        @Post
        @URL
        public void onPost() {
        }
        @Update
        @URL("/update")
        public void onUpdate() {
        }
        @Delete
        @URL("delete/")
        public void onDelete() {
        }

        public void shouldBeIgnored() {

        }
    }

    @Test
    void scanController() {
        ControllerScan scan = getControllerScan();
        ControllerSample sample = getInj().get(ControllerSample.class);
        Collection<Registration> registrations = scan.scanController(sample);
        assertEquals(4, registrations.size());

        Registration expectedPost = new Registration("/home/", RequestType.POST, null);
        assertTrue(registrations.contains(expectedPost));

        Registration expectedGet = new Registration("/home/", RequestType.GET, null);
        assertTrue(registrations.contains(expectedGet));

        Registration expectedDelete = new Registration("/home/delete/", RequestType.DELETE, null);
        assertTrue(registrations.contains(expectedDelete));

        Registration expectedUpdate = new Registration("/home/update/", RequestType.UPDATE, null);
        assertTrue(registrations.contains(expectedUpdate));
    }

    @Controller
    @RequestPrefix("wrong/")
    private static class ControllerWrong {
        @URL
        public void onGetNoAnnotation() {
        }
    }

    @Test
    void scanControllerError() {
        ControllerScan scan = getControllerScan();
        ControllerWrong wrong = getInj().get(ControllerWrong.class);
        assertThrows(ControllerException.class, () -> scan.scanController(wrong));
    }
}