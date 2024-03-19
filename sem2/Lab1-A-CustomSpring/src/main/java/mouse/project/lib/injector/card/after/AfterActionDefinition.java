package mouse.project.lib.injector.card.after;

import mouse.project.lib.injector.card.access.CardAccess;
import mouse.project.lib.injector.card.definition.Definition;

public interface AfterActionDefinition extends Definition {
    void callAction(Object callOn, CardAccess access);
}
