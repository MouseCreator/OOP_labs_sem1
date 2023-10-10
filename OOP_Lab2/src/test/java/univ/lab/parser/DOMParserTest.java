package univ.lab.parser;

import org.junit.jupiter.api.Test;
import univ.lab.fill.FillableCreator;
import univ.lab.fill.FillerImpl;
import univ.lab.model.Characteristics;
import univ.lab.model.Paper;
import univ.lab.model.Papers;

import static org.junit.jupiter.api.Assertions.*;

class DOMParserTest {

    @Test
    void parse() {
        FillableCreator fillableCreator = new FillableCreator();
        fillableCreator.add(Papers.class);
        fillableCreator.add(Paper.class);
        fillableCreator.add(Characteristics.class);
        DOMParser domParser = new DOMParser(fillableCreator, new FillerImpl());
        Papers papers = domParser.parse("src/test/resources/test-paper.xml");
        assertNotNull(papers);
        assertEquals(2, papers.getPapersList().size());
    }
}