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

            return (Papers) processNode(document.getDocumentElement());

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object processNode(Node node) {
        Object result = creator.createNew(node.getNodeName());
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            filler.fill(result, attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node detailNode = childNodes.item(i);
            if (creator.isElementDeclaration(detailNode.getNodeName())) {
                Object child = processNode(detailNode);
                filler.fill(result, detailNode.getNodeName(), child);
            } else {
                filler.fill(result, detailNode.getNodeName(), detailNode.getTextContent());
            }
        }
        return result;
    }

}
