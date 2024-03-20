package mouse.project.lib.injector.configuration;

import mouse.project.lib.annotation.Configuration;
import mouse.project.lib.annotation.Service;
import mouse.project.lib.exception.ConfigException;
import mouse.project.lib.exception.ScanException;
import mouse.project.lib.injector.InjectorBase;
import mouse.project.lib.injector.card.scan.PackageLoader;
import mouse.project.lib.modules.MouseModules;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ConfigurationScanner {

    public InjectorBase scan(Class<?> configClass) {
        Configuration config = configClass.getAnnotation(Configuration.class);
        if (config==null) {
            throw new ConfigException("No @Configuration annotation for " + configClass);
        }
        String name = config.name();
        InjectorBase injectorBase = new InjectorBase(configClass, name);
        scanAndAddAll(injectorBase, config);
        addSpecialClasses(config, injectorBase);
        processModules(injectorBase, config);
        return injectorBase;
    }

    private void processModules(InjectorBase base, Configuration config) {
        MouseModules[] mouseModules = config.includeModules();
        ModuleConfig moduleConfig = new ModuleConfig();
        Set<Class<?>> moduleClasses = moduleConfig.getModuleClasses(mouseModules);
        includeAll(base, moduleClasses);
    }

    private void addSpecialClasses(Configuration config, InjectorBase injectorBase) {
        Class<?>[] classes = config.includeClasses();
        includeAll(injectorBase, Set.of(classes));
    }

    private void includeAll(InjectorBase injectorBase, Collection<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            injectorBase.include(clazz);
        }
    }

    private Set<Class<?>> scanPackage(String basePackage) {
        PackageLoader loader = new PackageLoader();
        return loader.getAnnotatedClasses(basePackage, Service.class);
    }

    public void scanAndAddAll(InjectorBase injectorBase, Configuration config) {
        String basePackage = config.basePackage();
        Set<Class<?>> classes = scanPackage(basePackage);
        addClassesToIoc(injectorBase, classes);
    }
    private void addClassesToIoc(InjectorBase injectorBase, Collection<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            Service annotation = clazz.getAnnotation(Service.class);
            if (annotation == null) {
                throw new ScanException("Cannot scan class with no @Service annotation");
            }
            if (hasUseRestriction(annotation)) {
                addRestricted(clazz, injectorBase, annotation);
            } else {
                addToInjectorBase(injectorBase, clazz);
            }
        }
    }

    private void addToInjectorBase(InjectorBase injectorBase, Class<?> clazz) {
        injectorBase.include(clazz);
    }

    private void addRestricted(Class<?> clazz, InjectorBase injectorBase, Service annotation) {
        if (!injectorBase.isNamed()) {
            return;
        }
        String name = injectorBase.getName();
        boolean canUse = List.of(annotation.usedBy()).contains(name);
        if (canUse) {
            addToInjectorBase(injectorBase, clazz);
        }
    }

    private boolean hasUseRestriction(Service annotation) {
        return annotation.usedBy().length > 0;
    }
}