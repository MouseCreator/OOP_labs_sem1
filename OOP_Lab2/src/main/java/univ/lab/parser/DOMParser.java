package univ.lab.parser;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import univ.lab.fill.Filler;
import univ.lab.fill.FillableCreator;
import univ.lab.model.Papers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DOMParser implements Parser {

    private FillableCreator creator;
    private Filler filler;

    public DOMParser() {
    }

    public DOMParser(FillableCreator creator, Filler filler) {
        this.creator = creator;
        this.filler = filler;
    }

    @Override
    public Papers parse(String filename) {
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            document.getDocumentElement().normalize();

            processNode(document.getDocumentElement());

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return new Papers();
    }

    private Object processNode(Node node) {
        Element element = (Element) node;
        Object currentItem = creator.createNew(element.getTagName());

        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            filler.fill(currentItem, attribute.getNodeName(), attribute.getNodeValue());
        }

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                processNode(child);
            } else if (child.getNodeType() == Node.TEXT_NODE) {
                String nodeValue = child.getTextContent().trim();
            }
        }
        return currentItem;
    }

}
