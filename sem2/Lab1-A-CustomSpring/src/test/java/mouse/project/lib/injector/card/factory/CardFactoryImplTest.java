package mouse.project.lib.injector.card.factory;

import lombok.EqualsAndHashCode;
import mouse.project.lib.injector.card.container.CardContainer;
import mouse.project.lib.injector.card.container.CardContainerImpl;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.CardDefinition;
import mouse.project.lib.injector.card.scan.CardScanner;
import mouse.project.lib.injector.card.scan.CardScannerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CardFactoryImplTest {

    private CardFactoryImpl cardFactory;
    private CardDefinitions cardDefinitions;
    public CardScanner cardScanner;
    @BeforeEach
    void setUp() {
        cardScanner = new CardScannerImpl();
        cardDefinitions = new CardDefinitionsImpl();
        CardContainer cardContainer = new CardContainerImpl();
        cardFactory = new CardFactoryImpl(cardContainer, cardDefinitions);
    }
    @EqualsAndHashCode
    private static class SampleClass {

    }
    @Test
    void buildCard() {
        scanClass(SampleClass.class);
        SampleClass sampleClass = buildUnnamed(SampleClass.class);
        assertNotNull(sampleClass);
        assertEquals(sampleClass, new SampleClass());
    }

    private void scanClass(Class<?> clazz) {
        CardDefinition<?> definition = cardScanner.scan(clazz);
        cardDefinitions.add(definition);
    }
    private void scanAll(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            scanClass(clazz);
        }
    }

    private <T> T buildUnnamed(Class<T> t) {
        return cardFactory.buildCard(new Implementation<>(t, null));
    }
}