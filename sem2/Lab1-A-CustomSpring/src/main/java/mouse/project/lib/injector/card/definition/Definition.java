package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.container.Implementation;

import java.util.List;

public interface Definition {
    List<Implementation<?>> requiredImplementations();
}
