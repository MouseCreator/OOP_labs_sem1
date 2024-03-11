package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.injector.sources.RequiredClass;

public interface MultiRequirement extends Requirement {
    void satisfy(RequiredClass requiredClass, Object satisfyWith);
    void satisfy(int argument, Object satisfyWith);
}
