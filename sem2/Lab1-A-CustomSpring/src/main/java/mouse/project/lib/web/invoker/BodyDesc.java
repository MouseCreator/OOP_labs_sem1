package mouse.project.lib.web.invoker;

import java.util.Collection;

public interface BodyDesc {
    Class<?> expectedClass();
    boolean isCollection();
    boolean isFullBody();
    Class<? extends Collection<?>> collectionType();
    String attributeName();
}
