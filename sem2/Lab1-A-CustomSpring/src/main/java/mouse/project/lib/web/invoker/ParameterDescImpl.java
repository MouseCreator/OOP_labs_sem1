package mouse.project.lib.web.invoker;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;
@EqualsAndHashCode
@ToString
public final class ParameterDescImpl implements ParameterDesc {
    private final String name;
    private final Class<?> expectedType;
    private final ParamTranslation translations;

    private final String defaultValue;

    public ParameterDescImpl(String name,
                             String def,
                             Class<?> expectedType,
                             ParamTranslation translations) {
        this.name = name;
        this.expectedType = expectedType;
        this.translations = translations;
        this.defaultValue = def;
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
    public ParamTranslation translations() {
        return translations;
    }

}
