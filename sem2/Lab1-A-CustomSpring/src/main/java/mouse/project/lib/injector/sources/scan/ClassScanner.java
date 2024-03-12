package mouse.project.lib.injector.sources.scan;

import mouse.project.lib.injector.sources.producer.ClassProducer;

public interface ClassScanner {
    <T> ClassProducer scan(Class<T> clazz);
}
