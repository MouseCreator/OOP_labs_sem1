package mouse.project.lib.injector.card.scan;

import mouse.project.lib.exception.CardException;
import mouse.project.lib.injector.card.definition.CardDefinition;
import mouse.project.lib.injector.card.definition.DefinedCardImpl;
import mouse.project.lib.injector.sources.annotation.Construct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class CardScannerImplTest {

    private CardScanner scan;
    private ScanTestHelper helper;
    private interface InterfaceA {

    }
    private abstract class AbstractA {

    }
    private class NotStaticA {
    }
    @BeforeEach
    void setUp() {
        scan = new CardScannerImpl();
        helper = new ScanTestHelper();
    }

    @Test
    void scanInvalid() {
        assertThrows(CardException.class, () -> scan.scan(InterfaceA.class));
        assertThrows(CardException.class, () -> scan.scan(AbstractA.class));
        assertThrows(CardException.class, () -> scan.scan(NotStaticA.class));
    }
    private static class EmptyClass {
        @Construct(key = "1")
        public EmptyClass() {
        }
    }
    @Test
    void testEmpty() {
        CardDefinition<EmptyClass> cardDefinition = scan.scan(EmptyClass.class);
        DefinedCardImpl<EmptyClass> definedCard = (DefinedCardImpl<EmptyClass>) cardDefinition;
        assertNotNull(definedCard.getConstructor());

        Constructor<EmptyClass> constructor = definedCard.getConstructor().getConstructor();
        Construct annotation = constructor.getAnnotation(Construct.class);
        assertNotNull(annotation);
        assertEquals("1", annotation.key());

        Constructor<EmptyClass> constructor1 = helper.getConstructor(EmptyClass.class, "1");
        assertEquals(constructor1, constructor);
    }
}