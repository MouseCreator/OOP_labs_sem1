package univ.lab.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import univ.lab.fill.Fillable;
import univ.lab.fill.Filler;
import univ.lab.fill.FillableCreator;
import univ.lab.model.Paper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DOMParser implements Parser {

    private FillableCreator creator;
    private Filler filler;
    @Override
    public List<Paper> parse(String filename) {
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File("src/test/resources/example_jdom.xml"));
            document.getDocumentElement().normalize();

            NodeList papersOuterList = document.getElementsByTagName("papers");
            Node currentStack;
            for (int i=0; i<papersOuterList.getLength(); i++) {
                currentStack = papersOuterList.item(i);
                processNode(currentStack);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }

    private Fillable processNode(Node currentNode) {
        Fillable currentItem = creator.createNew(currentNode.getLocalName());
        NamedNodeMap attributes = currentNode.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            filler.fill(currentItem, attribute.getNodeName(), attribute.getNodeValue());
        }
        return null;
    }

    @Override
    public void write(String filename, List<Paper> instances) {

    }
}
