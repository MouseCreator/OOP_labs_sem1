package univ.lab.parser;

import org.xml.sax.SAXException;
import univ.lab.model.Paper;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class CustomSAXParser implements Parser {
    private SAXParserFactory SAXFactory;

    public CustomSAXParser(SAXParserFactory saxFactory) {
        SAXFactory = saxFactory;
    }

    public CustomSAXParser() {
    }

    @Override
    public List<Paper> parse(String filename) {
        try {
            SAXParser saxParser = SAXFactory.newSAXParser();
            SAXPaperHandler saxPaperHandler = new SAXPaperHandler();
            saxParser.parse(filename, saxPaperHandler);
            return null;//saxPaperHandler.getResult();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(String filename, List<Paper> instances) {

    }
}
