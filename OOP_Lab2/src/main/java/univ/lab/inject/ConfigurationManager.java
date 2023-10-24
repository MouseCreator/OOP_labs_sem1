package univ.lab.inject;

import univ.lab.fill.FillableCreator;

public class ConfigurationManager {
    private final FillableCreator fillableCreator = new FillableCreator();
    private final Injector injector = new InjectorImpl();
    public FillableCreator getFillableCreator() {
        return fillableCreator;
    }
    public Injector getInjector() {
        return injector;
    }
}
