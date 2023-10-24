package univ.lab.regular;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import univ.lab.model.Characteristics;
import univ.lab.model.Paper;
import univ.lab.model.Papers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RegularDOMParser implements RegularParser {
    @Override
    public Object parse(String filename) {
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

    private Object processDocument(Document document) {
        NodeList paperList = document.getElementsByTagName("paper");
        Papers papers = new Papers();
        papers.setPapersList(new ArrayList<>());
        for (int i = 0; i < paperList.getLength(); i++) {
            Node paperNode = paperList.item(i);
            Paper paper = new Paper();
            Characteristics characteristics = new Characteristics();
            paper.setCharacteristics(characteristics);
            if (paperNode.getNodeType() == Node.ELEMENT_NODE) {
                Element paperElement = (Element) paperNode;
                paper.setId(Long.parseLong(paperElement.getAttribute("id")));
                paper.setTitle(paperElement.getElementsByTagName("title").item(0).getTextContent());
                paper.setType(paperElement.getElementsByTagName("type").item(0).getTextContent());
                paper.setMonthly(Boolean.parseBoolean(paperElement.getElementsByTagName("monthly").item(0).getTextContent()));

                Element characteristicsElement = (Element) paperElement.getElementsByTagName("characteristics").item(0);
                characteristics.setIsColored(Boolean.parseBoolean(characteristicsElement.getElementsByTagName("colored").item(0).getTextContent()));
                characteristics.setVolume(Integer.parseInt(characteristicsElement.getElementsByTagName("volume").item(0).getTextContent()));
                characteristics.setIsGlossy(Boolean.parseBoolean(characteristicsElement.getElementsByTagName("glossy").item(0).getTextContent()));
                characteristics.setHasSubscriptionIndex(
                        Boolean.parseBoolean(characteristicsElement.getElementsByTagName("subscription_index").item(0).getTextContent()));
            }
        }
        return papers;
    }
}
