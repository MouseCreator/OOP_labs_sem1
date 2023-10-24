package univ.lab.parser;

import univ.lab.fill.FillableCreator;
import univ.lab.fill.Filler;
import univ.lab.fill.FillerImpl;
import univ.lab.model.Papers;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class StAXParser implements Parser {

    private final FillableCreator creator = new FillableCreator();
    private final Filler filler = new FillerImpl();

    @Override
    public Papers parse(String filename) {

        try {
            InputStream inputStream = new FileInputStream(filename);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
            return (Papers) parseElements(reader);
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Object parseElements(XMLStreamReader reader) throws XMLStreamException {
        return parseElements(null, reader, "");
    }

    private Object parseElements(Object result, XMLStreamReader reader, String element) throws XMLStreamException {
        String currentElement = element;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT -> {
                    currentElement = reader.getLocalName();
                    if (result == null) {
                        result = initializeWithAttributes(reader, currentElement);
                    } else {
                        Object childNode = initializeWithAttributes(reader, currentElement);
                        Object toFill = parseElements(childNode, reader, element);
                        filler.fill(result, currentElement, toFill);
                    }
                }
                case XMLStreamConstants.CHARACTERS -> {
                    String text = reader.getText().trim();
                    filler.fill(result, currentElement, text);
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    return result;
                }
            }
        }
        return null;
    }

    private Object initializeWithAttributes(XMLStreamReader reader, String currentElement) {
        Object result = creator.createNew(currentElement);
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            String name = reader.getAttributeName(i).toString();
            String value = reader.getAttributeValue(i);
            filler.fill(result, name, value);
        }
        return result;
    }
}
