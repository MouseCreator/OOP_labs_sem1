package mouse.project.lib.injector.card.requirement;

import mouse.project.lib.injector.card.container.Implementation;

import java.util.List;

public interface RequirementSet {
    List<Implementation<?>> getRequirements();
}
