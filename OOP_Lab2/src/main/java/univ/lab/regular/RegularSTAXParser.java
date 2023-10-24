package univ.lab.regular;

import univ.lab.model.Characteristics;
import univ.lab.model.Paper;
import univ.lab.model.Papers;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class RegularSTAXParser implements RegularParser{
    @Override
    public Object parse(String filename) {
        try {
            InputStream inputStream = new FileInputStream(filename);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
            return parseElements(reader);
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Object parseElements(XMLStreamReader reader) throws XMLStreamException {
        Object result = null;
        Papers currentPapers = null;
        Paper currentPaper = null;
        Characteristics currentCharacteristics = null;

        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT -> {
                    String elementName = reader.getLocalName();
                    switch (elementName) {
                        case "papers" -> {
                            currentPapers = new Papers();
                            currentPapers.setPapersList(new ArrayList<>());
                        }
                        case "paper" -> {
                            currentPaper = new Paper();
                            long id = Long.parseLong(reader.getAttributeValue(null, "id"));
                            currentPaper.setId(id);
                        }
                        case "characteristics" -> currentCharacteristics = new Characteristics();
                    }
                }

                case XMLStreamConstants.CHARACTERS -> {
                    String v = reader.getText().trim();
                    if (v.isEmpty())
                        continue;
                    if (currentPaper == null) {
                        throw new XMLStreamException();
                    }
                    switch (reader.getLocalName()) {
                        case "title" -> currentPaper.setTitle(v);
                        case "type" -> currentPaper.setType(v);
                        case "monthly" -> currentPaper.setMonthly(Boolean.parseBoolean(v));
                    }
                    if (currentCharacteristics == null) {
                        throw new XMLStreamException();
                    }
                    switch (reader.getLocalName()) {
                        case "colored" -> currentCharacteristics.setIsColored(Boolean.parseBoolean(v));
                        case "volume" -> currentCharacteristics.setVolume(Integer.parseInt(v));
                        case "glossy" -> currentCharacteristics.setIsGlossy(Boolean.parseBoolean(v));
                        case "subscription_index" -> currentCharacteristics.setHasSubscriptionIndex(Boolean.parseBoolean(v));
                    }
                }

                case XMLStreamConstants.END_ELEMENT -> {
                    String name = reader.getLocalName();
                    switch (name) {
                        case "papers" -> result = currentPapers;
                        case "paper" -> {
                            if (currentPapers == null)
                                throw new XMLStreamException();
                            currentPapers.getPapersList().add(currentPaper);
                        }
                        case "characteristics" -> {
                            if (currentPaper == null)
                                throw new XMLStreamException();
                            currentPaper.setCharacteristics(currentCharacteristics);
                        }
                    }
                }

            }
        }
        return result;
    }
}
