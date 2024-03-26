package mouse.project.lib.web.invoker;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;
@EqualsAndHashCode
@ToString
public final class ParameterDescImpl implements ParameterDesc {
    private final String name;
    private final Class<?> expectedType;

    private final String defaultValue;
    private final boolean bodySource;
    public ParameterDescImpl(String name,
                             String def,
                             Class<?> expectedType, boolean bodySource) {
        this.name = name;
        this.expectedType = expectedType;
        this.defaultValue = def;
        this.bodySource = bodySource;
    }

    @Override
    public Optional<String> defaultValue() {
        return Optional.ofNullable(defaultValue);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Class<?> expectedType() {
        return expectedType;
    }

    @Override
    public boolean bodySource() {
        return this.bodySource;
    }


}
