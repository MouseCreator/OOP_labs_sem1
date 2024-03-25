package mouse.project.lib.ioc.injector.configuration;

import mouse.project.lib.annotation.Configuration;
import mouse.project.lib.annotation.UseRestriction;
import mouse.project.lib.exception.ConfigException;
import mouse.project.lib.ioc.injector.AnnotationManager;
import mouse.project.lib.ioc.injector.InjectorBase;
import mouse.project.lib.ioc.injector.card.scan.PackageLoader;
import mouse.project.lib.modules.MouseModules;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigurationScanner {
    private final AnnotationManager annotationManager;
    public ConfigurationScanner(AnnotationManager annotationManager) {
        this.annotationManager = annotationManager;
    }

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
        List<Class<? extends Annotation>> targetAnnotations = annotationManager.getTargetAnnotations();
        Set<Class<?>> result = new HashSet<>();
        for (Class<? extends Annotation> target : targetAnnotations) {
            Set<Class<?>> annotatedClasses = loader.getAnnotatedClasses(basePackage, target);
            result.addAll(annotatedClasses);
        }
        return result;
    }

    public void scanAndAddAll(InjectorBase injectorBase, Configuration config) {
        String basePackage = config.basePackage();
        Set<Class<?>> classes = scanPackage(basePackage);
        addClassesToIoc(injectorBase, classes);
    }
    private void addClassesToIoc(InjectorBase injectorBase, Collection<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            annotationManager.validateService(clazz);
            if (hasUseRestriction(clazz)) {
                addRestricted(clazz, injectorBase);
            } else {
                addToInjectorBase(injectorBase, clazz);
            }
        }
    }

    private void addToInjectorBase(InjectorBase injectorBase, Class<?> clazz) {
        injectorBase.include(clazz);
    }

    private void addRestricted(Class<?> clazz, InjectorBase injectorBase) {
        if (!injectorBase.isNamed()) {
            return;
        }
        UseRestriction restricted = clazz.getAnnotation(UseRestriction.class);
        assert restricted != null;
        String name = injectorBase.getName();
        boolean canUse = List.of(restricted.usedBy()).contains(name);
        if (canUse) {
            addToInjectorBase(injectorBase, clazz);
        }
    }

    private boolean hasUseRestriction(Class<?> clazz) {
        UseRestriction annotation = clazz.getAnnotation(UseRestriction.class);
        if (annotation == null) {
            return false;
        }
        return annotation.usedBy().length > 0;
    }
}