package mouse.project.lib.injector.sources.scan;

import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.producer.ClassProducer;
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

    private static class SampleB {
        private final String str;

        public SampleB() {
            str = "default";
        }
        public SampleB(String d) {
            str = d;
        }
    }
    @Test
    void testDefaultConstructorCalled() {
        ClassProducer scanned = scan.scan(SampleB.class);
        SampleB sampleB = (SampleB) scanned.produceClass();
        assertEquals("default", sampleB.str);
    }
}