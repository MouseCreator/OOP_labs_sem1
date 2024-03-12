package mouse.project.lib.injector.sources.scan;

import mouse.project.lib.injector.sources.producer.ClassProducer;

public interface ClassScanner {
    <T> ClassProducer<T> scan(Class<T> clazz);
}
