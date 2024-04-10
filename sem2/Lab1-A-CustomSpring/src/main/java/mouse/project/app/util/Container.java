package mouse.project.app.util;

import mouse.project.app.config.ConfigClass;
import mouse.project.lib.ioc.Inj;
import mouse.project.lib.ioc.Ioc;

public class Container {
    public static Inj get() {
        return Ioc.getConfiguredInjector(ConfigClass.class);
    }
}
