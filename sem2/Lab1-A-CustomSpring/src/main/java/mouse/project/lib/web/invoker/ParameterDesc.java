package mouse.project.lib.web.invoker;

import java.util.Optional;

public interface ParameterDesc {
    Class<?> expectedType();
    boolean bodySource();
    String name();
    Optional<String> defaultValue();
}
