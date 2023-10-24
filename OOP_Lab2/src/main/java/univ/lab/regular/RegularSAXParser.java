package univ.lab.regular;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class RegularSAXParser implements RegularParser {
    private final SAXParserFactory SAXFactory;


    public RegularSAXParser(SAXParserFactory saxFactory) {
        SAXFactory = saxFactory;
    }

    @Override
    public Object parse(String filename) {
        try {
            SAXParser saxParser = SAXFactory.newSAXParser();
            RegularSAXHandler saxPaperHandler = new RegularSAXHandler();
            saxParser.parse(filename, saxPaperHandler);
            return saxPaperHandler.getResult();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
