package mouse.project.lib.web.setup;

import mouse.project.lib.ioc.Ioc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetUpWebTest {

    @Test
    void scanAndStart() {
        Class<ConfigurationClass> config = ConfigurationClass.class;
        SetUpWeb setUpWeb = Ioc.getConfiguredInjector(config).get(SetUpWeb.class);
        assertDoesNotThrow(() -> setUpWeb.scanAndStart(config));
    }
}