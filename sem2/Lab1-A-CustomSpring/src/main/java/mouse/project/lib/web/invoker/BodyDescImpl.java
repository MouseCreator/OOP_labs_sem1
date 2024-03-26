package mouse.project.lib.web.invoker;

import java.util.Collection;

public class BodyDescImpl implements BodyDesc {

    private final Class<? extends Collection<?>> collectionType;
    private final Class<?> expectedType;
    private final String name;

    public BodyDescImpl(Class<? extends Collection<?>> collectionType, Class<?> expectedType, String name) {
        this.collectionType = collectionType;
        this.expectedType = expectedType;
        this.name = name;
    }

    public BodyDescImpl(Class<?> expectedType, String name) {
        this.expectedType = expectedType;
        this.name = name;
        collectionType = null;
    }

    public BodyDescImpl(Class<?> expectedType) {
        this.expectedType = expectedType;
        name = "";
        collectionType = null;
    }

    @Override
    public Class<?> expectedClass() {
        return expectedType;
    }

    @Override
    public boolean isCollection() {
        return collectionType != null;
    }

    @Override
    public boolean isFullBody() {
        return name == null || name.isEmpty();
    }

    @Override
    public Class<? extends Collection<?>> collectionType() {
        return collectionType;
    }

    @Override
    public String attributeName() {
        return name;
    }
}
