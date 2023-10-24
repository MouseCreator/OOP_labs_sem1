package univ.lab.parser;

import univ.lab.fill.FillableCreator;
import univ.lab.model.Characteristics;
import univ.lab.model.Paper;
import univ.lab.model.Papers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractParserTest {
    protected FillableCreator createFillerCreator() {
        FillableCreator fillableCreator = new FillableCreator();
        fillableCreator.add(Papers.class);
        fillableCreator.add(Paper.class);
        fillableCreator.add(Characteristics.class);
        return fillableCreator;
    }
    private List<Paper> expectedPapers() {
        List<Paper> papers = new ArrayList<>();
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

        Paper expectedPaper2 = new Paper();
        expectedPaper2.setId(2L);
        expectedPaper2.setTitle("Poem Times");
        expectedPaper2.setType("Magazine");
        expectedPaper2.setMonthly(false);

        Characteristics characteristics2 = new Characteristics();
        characteristics2.setVolume(15);
        characteristics2.setIsColored(false);
        characteristics2.setHasSubscriptionIndex(true);
        characteristics2.setIsGlossy(false);

        expectedPaper1.setCharacteristics(characteristics1);
        expectedPaper2.setCharacteristics(characteristics2);

        papers.add(expectedPaper1);
        papers.add(expectedPaper2);
        return papers;
    }
    public void test(Parser parser) {
        List<Paper> expectedPapers = expectedPapers();

        FillableCreator fillableCreator = new FillableCreator();
        fillableCreator.add(Papers.class);
        fillableCreator.add(Paper.class);
        fillableCreator.add(Characteristics.class);
        Papers papers = (Papers) parser.parse("src/test/resources/test-paper.xml");
        List<Paper> list = papers.getPapersList();
        assertEquals(2, list.size());
        Paper paper1 = list.get(0);
        assertEquals(expectedPapers.get(0), paper1);

        Paper paper2 = list.get(1);
        assertEquals(expectedPapers.get(1), paper2);
    }
}
