package univ.lab.demo;

import univ.lab.inject.Injector;
import univ.lab.inject.InjectorFactory;
import univ.lab.model.Characteristics;
import univ.lab.model.Paper;
import univ.lab.model.Papers;
import univ.lab.parser.Parser;
import univ.lab.validator.XSDValidator;

public class SimpleDemonstration implements Demonstration{

    public static final String CONFIG_XSD = "src/main/resources/xsd/injector-config.xsd";
    public static final String CONFIG_XML = "src/main/resources/xml/injector-config.xml";
    public static final String PAPERS_XSD = "src/main/resources/xsd/paper.xsd";
    public static final String PAPERS_XML = "src/main/resources/xml/paper.xml";
    public void validateAndParse() {
        XSDValidator validator = new XSDValidator();
        try {
            validator.validate(PAPERS_XSD, PAPERS_XML);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(validator.isValid(CONFIG_XSD, CONFIG_XML)){
            demoParsing();
        } else {
            System.err.println("Provided Configuration XML is not Valid!");
        }
    }

    private void demoParsing() {
        Injector injector = createInjector();
        Parser parser = injector.getInstance(Parser.class);

        System.out.println("Parser: " + parser.getClass().getSimpleName());

        Papers papers = (Papers) parser.parse(PAPERS_XML);
        print(papers);
    }

    private void print(Papers papers) {
        StringBuilder builder = new StringBuilder("PAPERS\n");
        for (Paper paper : papers.getPapersList()) {
            builder.append(String.format("Paper %d {\n", paper.getId()));
            builder.append(String.format("\ttitle=%s\n", paper.getTitle()));
            builder.append(String.format("\tmonthly=%s\n", paper.getMonthly()));
            Characteristics characteristics = paper.getCharacteristics();
            builder.append("\tCharacteristics {\n");
            builder.append(String.format("\t\tcolored=%s\n", characteristics.getIsColored()));
            builder.append(String.format("\t\tvolume=%d\n", characteristics.getVolume()));
            builder.append(String.format("\t\tglossy=%s\n", characteristics.getIsGlossy()));
            builder.append(String.format("\t\thas_index=%s\n", characteristics.getHasSubscriptionIndex()));
            builder.append("\t}\n}\n");
        }
        System.out.println(builder);
    }

    private Injector createInjector() {
        InjectorFactory injectorFactory = new InjectorFactory();
        return injectorFactory.fromConfiguration(CONFIG_XML);
    }
}
