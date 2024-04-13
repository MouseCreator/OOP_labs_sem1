package mouse.project.lib.data.orm.map;

public interface OrmContext {
    OrmMap getMap(Class<?> configClass);

    void create(Class<?> configClass);
}
