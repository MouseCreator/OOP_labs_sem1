package mouse.project.lib.web.factory.translations;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.utils.Utils;
import mouse.project.lib.web.exception.TranslationException;
import mouse.project.lib.web.invoker.ParamTranslation;

import java.util.HashMap;
import java.util.Map;

@Service
public class ParamTranslationFactoryImpl implements ParamTranslationFactory {
    private final Map<Class<?>, ParamTranslation> preparedMap;

    @Auto
    public ParamTranslationFactoryImpl() {
        preparedMap = new HashMap<>();
        prepare(preparedMap);
    }

    private void prepare(Map<Class<?>, ParamTranslation> preparedMap) {
        preparedMap.put(Integer.class, Integer::parseInt);
        preparedMap.put(Float.class, Float::parseFloat);
        preparedMap.put(Double.class, Double::parseDouble);
        preparedMap.put(String.class, s->s);
        preparedMap.put(Short.class, Short::parseShort);
        preparedMap.put(Byte.class, Byte::parseByte);
        preparedMap.put(Object.class, s->s);
    }

    @Override
    public ParamTranslation create(Class<?> type) {
        Class<?> target = Utils.fromPrimitive(type);
        ParamTranslation translation = preparedMap.get(target);
        if (translation != null) {
            return translation;
        }
        throw new TranslationException("Cannot translate string to parameter. Unexpected type: " + type);

    }

}
