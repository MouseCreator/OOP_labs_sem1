package mouse.project.lib.injector.sources.scan;

import mouse.project.lib.exception.IOCException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassScannerImplTest {

    private ClassScanner scan;

    @BeforeEach
    void setUp() {
        scan = new ClassScannerImpl();
    }


    private interface InterfaceA {
    }
    private static abstract class AbstractA {

    }
    private class NotStaticA {
    }
    private static class StaticA {
    }
    @Test
    void testTypesToScan() {
        assertThrows(IOCException.class, () -> scan.scan(InterfaceA.class));
        assertThrows(IOCException.class, () -> scan.scan(AbstractA.class));
        assertThrows(IOCException.class, () -> scan.scan(NotStaticA.class));
        assertDoesNotThrow(() -> scan.scan(StaticA.class));
    }
}