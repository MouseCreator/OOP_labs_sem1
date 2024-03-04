package univ.lab.parser;

import org.junit.jupiter.api.Test;
import univ.lab.fill.FillerImpl;

import javax.xml.parsers.SAXParserFactory;


class CustomSAXParserTest extends AbstractParserTest{
    @Test
    void parse() {
        CustomSAXParser parser = new CustomSAXParser(SAXParserFactory.newInstance(), new FillerImpl(), createFillerCreator());
        test(parser);
    }
}