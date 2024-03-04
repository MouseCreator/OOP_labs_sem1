package univ.lab.parser;

import org.xml.sax.SAXException;
import univ.lab.fill.FillableCreator;
import univ.lab.fill.Filler;
import univ.lab.inject.Inject;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class CustomSAXParser implements ReflectParser {

    @Inject
    private SAXParserFactory SAXFactory;

    @Inject
    private Filler filler;

    @Inject
    private FillableCreator fillableCreator;
    public CustomSAXParser() {}
    public CustomSAXParser(SAXParserFactory saxFactory, Filler filler, FillableCreator fillableCreator) {
        SAXFactory = saxFactory;
        this.filler = filler;
        this.fillableCreator = fillableCreator;
    }

    @Override
    public Object parse(String filename) {
        try {
            SAXParser saxParser = SAXFactory.newSAXParser();
            SAXPaperHandler saxPaperHandler = new SAXPaperHandler();
            saxPaperHandler.setCreator(fillableCreator);
            saxPaperHandler.setFiller(filler);
            saxParser.parse(filename, saxPaperHandler);
            return saxPaperHandler.getResult();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
