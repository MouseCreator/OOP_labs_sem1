package mouse.project.lib.data.orm.scan;

import mouse.project.lib.data.orm.desc.ModelDescription;

import java.util.Collection;

public interface ModelScanner {
    Collection<ModelDescription<?>> scanAndDescribe(String packageName);
}
