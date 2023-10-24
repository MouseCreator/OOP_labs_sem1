package univ.lab.regular;

import org.junit.jupiter.api.Test;
import univ.lab.parser.AbstractParserTest;

class RegularDOMParserTest extends AbstractParserTest {

    @Test
    void parse() {
        RegularDOMParser regularDOMParser = new RegularDOMParser();
        test(regularDOMParser);
    }
}