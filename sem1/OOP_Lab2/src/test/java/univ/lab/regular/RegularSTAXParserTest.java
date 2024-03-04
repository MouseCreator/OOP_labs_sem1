package univ.lab.regular;

import org.junit.jupiter.api.Test;
import univ.lab.parser.AbstractParserTest;
class RegularSTAXParserTest extends AbstractParserTest {

    @Test
    void parse() {
        RegularSTAXParser regularSTAXParser = new RegularSTAXParser();
        test(regularSTAXParser);
    }
}