package univ.lab.parser;

import org.junit.jupiter.api.Test;
import univ.lab.fill.FillableCreator;
import univ.lab.fill.FillerImpl;
import univ.lab.model.Characteristics;
import univ.lab.model.Paper;
import univ.lab.model.Papers;

import javax.xml.parsers.SAXParserFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomSAXParserTest {

    @Test
    void parse() {
        Paper expectedPaper1 = new Paper();
        expectedPaper1.setId(1L);
        expectedPaper1.setTitle("World News");
        expectedPaper1.setType("Newspaper");
        expectedPaper1.setMonthly(true);

        Characteristics characteristics1 = new Characteristics();
        characteristics1.setVolume(30);
        characteristics1.setIsColored(true);
        characteristics1.setHasSubscriptionIndex(true);
        characteristics1.setIsGlossy(true);

        expectedPaper1.setCharacteristics(characteristics1);

        FillableCreator fillableCreator = new FillableCreator();
        fillableCreator.add(Papers.class);
        fillableCreator.add(Paper.class);
        fillableCreator.add(Characteristics.class);
        CustomSAXParser parser = new CustomSAXParser(SAXParserFactory.newInstance(), new FillerImpl(), fillableCreator);
        Papers papers = (Papers) parser.parse("src/test/resources/test-paper.xml");
        List<Paper> list = papers.getPapersList();
        assertEquals(2, list.size());
        Paper paper1 = list.get(0);
        assertEquals(expectedPaper1, paper1);

    }
}