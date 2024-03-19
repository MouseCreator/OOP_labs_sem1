package mouse.project.lib.injector.card.helper;

import mouse.project.lib.injector.card.container.Implementation;

public record FieldInfo(Implementation<?> implementation, Class<?> collectionType) {
}
