package mouse.project.lib.ioc;

import mouse.project.lib.annotation.Primary;
import mouse.project.lib.annotation.Name;
import mouse.project.lib.exception.IOCException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IocContainerImplTest {

    private IocContainerImpl iocContainer;

    private interface InterfaceWithOneImplementation {
        String method();
    }

    private static class SingleImplementation implements InterfaceWithOneImplementation {
        public String method() {
            return "SingleImplementation";
        }
    }

    private interface InterfaceWithPrimaryImplementation {
        String method();
    }
    @Primary
    private static class PrimaryImplementation implements InterfaceWithPrimaryImplementation {
        public String method() {
            return "Primary";
        }
    }

    private static class NotPrimaryImplementation implements InterfaceWithPrimaryImplementation {
        public String method() {
            return "Regular";
        }
    }

    private interface InterfaceWithTwoPrimaryImplementations {
    }
    @Primary
    private static class PrimaryImplementationOne implements InterfaceWithTwoPrimaryImplementations {
    }
    @Primary
    private static class PrimaryImplementationTwo implements InterfaceWithTwoPrimaryImplementations {
    }

    private interface InterfaceWithTwoNoPrimaryImplementations {
    }
    private static class NotPrimaryImplementationOne implements InterfaceWithTwoNoPrimaryImplementations {
    }
    private static class NotPrimaryImplementationTwo implements InterfaceWithTwoNoPrimaryImplementations {
    }

    private interface InterfaceWithNamedImplementation {
    }
    @Primary
    private static class PrimaryNotNamed implements InterfaceWithNamedImplementation {
    }
    @Name(name = "notPrimaryNamed")
    private static class NotPrimaryNamed implements InterfaceWithNamedImplementation {
    }
    @BeforeEach
    void setUp() {
        iocContainer = new IocContainerImpl();
    }

    @Test
    void getSingle_noConflict() {
        iocContainer.addImplementation(new SingleImplementation());
        SingleImplementation single = iocContainer.getSingle(SingleImplementation.class);
        assertEquals("SingleImplementation", single.method());

        InterfaceWithOneImplementation fromInterface = iocContainer.getSingle(InterfaceWithOneImplementation.class);
        assertEquals("SingleImplementation", fromInterface.method());
    }

    @Test
    void getSingle_hasPrimary() {
        iocContainer.addImplementation(new PrimaryImplementation());
        iocContainer.addImplementation(new NotPrimaryImplementation());

        InterfaceWithPrimaryImplementation primary = iocContainer.getSingle(InterfaceWithPrimaryImplementation.class);
        assertEquals(PrimaryImplementation.class, primary.getClass());
        assertEquals("Primary", primary.method());
    }

    @Test
    void getSingle_twoPrimary() {
        iocContainer.addImplementation(new PrimaryImplementationOne());
        iocContainer.addImplementation(new PrimaryImplementationTwo());

        assertThrows(IOCException.class, () ->
                iocContainer.getSingle(InterfaceWithTwoPrimaryImplementations.class));
    }

    @Test
    void getSingle_noPrimary() {
        iocContainer.addImplementation(new NotPrimaryImplementationOne());
        iocContainer.addImplementation(new NotPrimaryImplementationTwo());

        assertThrows(IOCException.class, () ->
                iocContainer.getSingle(InterfaceWithTwoNoPrimaryImplementations.class));
    }

    @Test
    void getNamed() {
        iocContainer.addImplementation(new PrimaryNotNamed());
        iocContainer.addImplementation(new NotPrimaryNamed());

        InterfaceWithNamedImplementation primary = iocContainer.getSingle(InterfaceWithNamedImplementation.class);
        assertEquals(PrimaryNotNamed.class, primary.getClass());

        InterfaceWithNamedImplementation named = iocContainer.getNamed(InterfaceWithNamedImplementation.class,
                "notPrimaryNamed");
        assertEquals(NotPrimaryNamed.class, named.getClass());
    }

    @Test
    void getList() {
    }

    @Test
    void getSet() {
    }
}