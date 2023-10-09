package univ.lab.inject;

public class InjectorUtil {
    private static Injector injector = null;

    public Injector get() {
        if (injector == null) {
            injector = initializeInjector();
        }
        return injector;
    }

    private Injector initializeInjector() {
        String INIT_FILE = "src/main/resources/xml/injector-config.xml";
        InjectorInitializer injectorInitializer = new InjectorInitializer();
        return injectorInitializer.initialize(INIT_FILE);
    }
}
