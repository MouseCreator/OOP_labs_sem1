package mouse.project.lib.injector.card.scan;

import mouse.project.lib.annotation.Service;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Service
class PackageLoaderTest {
    @Service
    private static class Inner {

    }

    @Test
    void testOnSelf() {
        PackageLoader packageLoader = new PackageLoader();
        List<Class<?>> classes = packageLoader.getClasses("mouse.project.lib.injector.card.scan", Service.class);
        assertTrue(classes.contains(PackageLoaderTest.class));
        assertTrue(classes.contains(Inner.class));
    }
}