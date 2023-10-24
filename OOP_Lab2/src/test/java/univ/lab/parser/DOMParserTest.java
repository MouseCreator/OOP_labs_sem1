package univ.lab.parser;

import org.junit.jupiter.api.Test;
import univ.lab.fill.FillerImpl;

class DOMParserTest extends AbstractParserTest {

    @Test
    void parse() {
        DOMParser domParser = new DOMParser(createFillerCreator(), new FillerImpl());
        test(domParser);
    }
}