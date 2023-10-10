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
import java.util.Stack;

public class StAXParser implements Parser{

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
        String bufferedTag = "";
        Object result = null;
        Object currentObject = null;
        String current = "";
        Stack<Object> stack = new Stack<>();
        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
            case XMLStreamConstants.START_ELEMENT:
                break;
            case XMLStreamConstants.CHARACTERS:
                break;
            case XMLStreamConstants.END_ELEMENT:
                break;
            }
        }
        return null;
    }
}
