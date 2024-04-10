package mouse.project.app.config;

import mouse.project.lib.ioc.annotation.Configuration;
import mouse.project.lib.modules.MouseModules;

@Configuration(basePackage = "mouse.project.app", includeModules = MouseModules.WEB_MODULE)
public class ConfigClass {
}
