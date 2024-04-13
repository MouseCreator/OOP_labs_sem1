package mouse.project.lib.data.orm.map;

import mouse.project.lib.data.orm.exception.ORMException;
import mouse.project.lib.ioc.annotation.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrmContextImpl implements OrmContext {

    public OrmContextImpl() {
        map = new HashMap<>();
    }

    private final Map<Class<?>, OrmMap> map;
    @Override
    public OrmMap getMap(Class<?> configClass) {
        OrmMap ormMap = map.get(configClass);
        if (ormMap == null) {
            throw new ORMException("No models map defined for configuration: " + map);
        }
        return ormMap;
    }

    @Override
    public void create(Class<?> configClass) {
        map.put(configClass, new OrmMapImpl());
    }
}
