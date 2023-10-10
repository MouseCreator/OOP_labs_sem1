package univ.lab.parser;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import univ.lab.fill.Fillable;
import univ.lab.fill.FillableCreator;
import univ.lab.fill.Filler;
import java.util.Stack;

public class SAXPaperHandler extends DefaultHandler {
    private Object result = null;
    private Fillable currentElement;
    private final Stack<Fillable> stack = new Stack<>();
    private FillableCreator creator;
    private Filler filler;
    private String bufferedValue;

    public void setCreator(FillableCreator creator) {
        this.creator = creator;
    }

    public void setFiller(Filler filler) {
        this.filler = filler;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        if (isElementDeclaration(qName)) {
            if (currentElement != null) {
                stack.push(currentElement);
            }
            processNewElement(qName, attributes);
        }

    }
    @Override
    public void characters(char[] chars, int start, int length) {
        bufferedValue = new String(chars, start, length);
    }
    @Override
    public void endElement(String uri, String localName, String qName) {
        if (isElementDeclaration(qName)) {
            Fillable finishedElement = currentElement;
            currentElement = stack.pop();
            if (currentElement == null) {
                result = finishedElement;
            } else {
                filler.fill(currentElement, qName, finishedElement);
            }
        }
        filler.fill(currentElement, qName, bufferedValue);
    }

    private void processNewElement(String qName, Attributes attributes) {
        currentElement = createNewCurrent(qName);
        for (int i = 0; i < attributes.getLength(); i++) {
            String attributeName = attributes.getQName(i);
            String attributeValue = attributes.getValue(i);
            filler.fill(currentElement, attributeName, attributeValue);
        }
    }

    private Fillable createNewCurrent(String qName) {
        return creator.createNew(qName);
    }

    private boolean isElementDeclaration(String qName) {
        return creator.isElementDeclaration(qName);
    }

    public Object getResult() {
        return result;
    }
}