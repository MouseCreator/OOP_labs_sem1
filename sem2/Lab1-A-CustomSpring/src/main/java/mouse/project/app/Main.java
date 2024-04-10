package mouse.project.app;

import mouse.project.app.config.ConfigClass;
import mouse.project.app.util.Container;
import mouse.project.lib.web.setup.SetUpWeb;

public class Main {
    public static void main(String[] args) {
        SetUpWeb setUpWeb = Container.get().get(SetUpWeb.class);
        setUpWeb.scanAndStart(ConfigClass.class);
    }
}
