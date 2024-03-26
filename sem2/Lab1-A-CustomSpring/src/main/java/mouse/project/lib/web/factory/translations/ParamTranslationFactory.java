package mouse.project.lib.web.factory.translations;

import mouse.project.lib.web.invoker.ParamTranslation;

public interface ParamTranslationFactory {
    ParamTranslation create(Class<?> type);
}
