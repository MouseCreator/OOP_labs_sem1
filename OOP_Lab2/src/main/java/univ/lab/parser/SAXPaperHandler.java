package univ.lab.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import univ.lab.model.Paper;

import java.util.ArrayList;
import java.util.List;

public class SAXPaperHandler extends DefaultHandler {
    private final List<Paper> papers  = new ArrayList<>();
    private boolean inPaper;
    private Paper currentElement;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (inPaper) {

        }

        if (qName.equalsIgnoreCase("paper")) {
            inPaper = true;
            currentElement = new Paper();
            String id = attributes.getValue("id");
            currentElement.setId(Long.getLong(id));
        }
    }



    public List<Paper> getResult() {
        return papers;
    }
}
