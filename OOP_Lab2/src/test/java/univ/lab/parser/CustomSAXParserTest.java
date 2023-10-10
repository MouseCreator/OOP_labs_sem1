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
        FillableCreator fillableCreator = new FillableCreator();
        fillableCreator.add(Papers.class);
        fillableCreator.add(Paper.class);
        fillableCreator.add(Characteristics.class);
        CustomSAXParser parser = new CustomSAXParser(SAXParserFactory.newInstance(), new FillerImpl(), fillableCreator);
        Papers papers = parser.parse("src/test/resources/test-paper.xml");
        List<Paper> list = papers.getPapersList();
        assertEquals(2, list.size());
        Paper paper1 = list.get(0);

    }
}