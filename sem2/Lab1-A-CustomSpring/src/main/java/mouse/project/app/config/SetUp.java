package mouse.project.app.config;

import mouse.project.app.util.Container;
import mouse.project.lib.data.SetUpData;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.setup.SetUpWeb;

@Service
public class SetUp {
    public void startApp() {
        Container.get().get(SetUpWeb.class).scanAndStart(ConfigClass.class);
        Container.get().get(SetUpData.class).scanModels(ConfigClass.class);
    }
}
