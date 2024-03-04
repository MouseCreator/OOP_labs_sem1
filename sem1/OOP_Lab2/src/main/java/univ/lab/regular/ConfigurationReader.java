package univ.lab.regular;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import univ.lab.inject.ConfigurationManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ConfigurationReader {
    public ConfigurationManager parse(String filename) {
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            document.getDocumentElement().normalize();
            return processDocument(document);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ConfigurationManager processDocument(Document document) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        processInjections(document, configurationManager);
        processCreations(document, configurationManager);
        return configurationManager;
    }

    private void processCreations(Document document, ConfigurationManager configurationManager) {
        NodeList creations = document.getElementsByTagName("creation");
        for (int i = 0; i < creations.getLength(); i++) {
            Node creationNode = creations.item(i);
            if (creationNode.getNodeType() == Node.ELEMENT_NODE) {
                Element creationElement = (Element) creationNode;
                String creationValue = creationElement.getTextContent().trim();
                try {
                    configurationManager.getFillableCreator().add(Class.forName(creationValue));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void processInjections(Document document, ConfigurationManager configurationManager) {
        NodeList injections = document.getElementsByTagName("injection");
        for (int i = 0; i < injections.getLength(); i++) {
            Node paperNode = injections.item(i);
            if (paperNode.getNodeType() == Node.ELEMENT_NODE) {
                Element paperElement = (Element) paperNode;
                String interfaceS = paperElement.getAttribute("interface");
                String classS = paperElement.getAttribute("class");
                try {
                    configurationManager.getInjector().addImplementation(Class.forName(interfaceS), Class.forName(classS));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
