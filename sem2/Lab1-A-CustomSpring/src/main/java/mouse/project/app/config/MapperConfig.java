package mouse.project.app.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueCheckStrategy;

@org.mapstruct.MapperConfig(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl"
)
public class MapperConfig {
}
