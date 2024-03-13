package mouse.project.lib.injector.card.producer;

public interface DefinedProducer<T> extends CardProducer<T> {
    void setConstructor(ConstructorProducer<T> constructor);
    void addSetter(SetterProducer<T> setterProducer);
    void addFieldInjection(FieldProducer<T> fieldProducer);
}