package mouse.project.lib.web.invoker;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.ioc.injector.card.helper.CollectionProducer;
import mouse.project.lib.web.parse.BodyParser;
import mouse.project.lib.web.request.RequestBody;

import java.util.Collection;
@Service
public class BodyProcessorImpl implements BodyProcessor {

    private final BodyParser parser;
    @Auto
    public BodyProcessorImpl(BodyParser parser) {
        this.parser = parser;
    }

    public Object getBodyInfo(RequestBody body, BodyDesc desc) {
        CollectionProducer collectionProducer = new CollectionProducer();
        String str = body.get();

        String attr = desc.attributeName();
        Object param;
        if (attr == null || attr.isEmpty()) {
            if (desc.isCollection()) {
                Collection<?> parsedValues = parser.parseAll(str, desc.expectedClass());
                param = collectionProducer.create(desc.collectionType(), parsedValues);
            } else {
                param = parser.parse(str, desc.expectedClass());
            }
        } else {
            if (desc.isCollection()) {
                Collection<?> parsedValues = parser.parseAllByAttribute(str, attr, desc.expectedClass());
                param = collectionProducer.create(desc.collectionType(), parsedValues);
            } else {
                param = parser.parseAttribute(str, attr, desc.expectedClass());
            }
        }
        return param;
    }
}
