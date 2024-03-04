package univ.lab.parser;

import org.junit.jupiter.api.Test;
import univ.lab.fill.FillerImpl;

class StAXParserTest extends AbstractParserTest {

    @Test
    void parse() {

        StAXParser stAXParser = new StAXParser(createFillerCreator(), new FillerImpl());
        test(stAXParser);
    }
}