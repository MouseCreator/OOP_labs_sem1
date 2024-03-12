package mouse.project.lib.injector.sources.producer;

public interface ClassProducer<T> extends Producer {
    T produceClass();
}
