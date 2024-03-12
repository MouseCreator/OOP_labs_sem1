package mouse.project.lib.injector.sources.producer;


import mouse.project.lib.injector.sources.RequiredClass;

public interface ClassProducer<T> extends Producer {
    T produceClass();
    void satisfy(RequiredClass requiredClass, Object with);
}
