package mouse.project.lib.injector.configuration;

import mouse.project.lib.modules.MouseModules;

import java.util.HashSet;
import java.util.Set;

public class ModuleConfig {
    public Set<Class<?>> getModuleClasses(MouseModules... modules) {
        Set<Class<?>> result = new HashSet<>();
        for (MouseModules module : modules) {
            Set<Class<?>> singleModuleClasses = getSingleModuleClasses(module);
            result.addAll(singleModuleClasses);
        }
        return result;
    }

    private Set<Class<?>> getSingleModuleClasses(MouseModules module) {
        return Set.of();
    }
}
