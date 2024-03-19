package mouse.project.lib.injector.card.factory;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mouse.project.lib.annotation.Auto;
import mouse.project.lib.annotation.Collect;
import mouse.project.lib.injector.card.container.CardContainer;
import mouse.project.lib.injector.card.container.CardContainerImpl;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.CardDefinition;
import mouse.project.lib.injector.card.scan.CardScanner;
import mouse.project.lib.injector.card.scan.CardScannerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Data
    private static class Dependency {
        private final String str;

        public Dependency() {
            str = "Text";
        }
    }
    @Data
    private static class Dependant {
        @Auto
        private Dependency dependency;
    }
    @Test
    void buildWithDependency() {
        scanAll(Dependency.class, Dependant.class);
        Dependant dependant = buildUnnamed(Dependant.class);
        assertNotNull(dependant);
        assertNotNull(dependant.getDependency());
        assertEquals("Text", dependant.getDependency().getStr());
    }
    @Data
    private static class ListDependant {
        @Auto
        @Collect(Dependency.class)
        private List<Dependency> dependency;
    }
    @Test
    void buildWithListDependency() {
        scanAll(ListDependant.class, Dependant.class);
        ListDependant dependant = buildUnnamed(ListDependant.class);
        assertNotNull(dependant);
        List<Dependency> dependency = dependant.getDependency();
        assertEquals(1, dependency.size());
        assertEquals(ArrayList.class, dependency.getClass());
        assertEquals(new Dependency(), dependency.get(0));
    }


}