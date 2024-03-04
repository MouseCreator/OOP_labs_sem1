package univ.lab.regular;

import org.junit.jupiter.api.Test;
import univ.lab.parser.AbstractParserTest;

import javax.xml.parsers.SAXParserFactory;

class RegularSAXParserTest extends AbstractParserTest {

    @Test
    void parse() {
        RegularSAXParser regularSAXParser = new RegularSAXParser(SAXParserFactory.newInstance());
        test(regularSAXParser);
    }
}