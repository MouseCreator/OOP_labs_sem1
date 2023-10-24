package univ.lab.regular;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import univ.lab.model.Characteristics;
import univ.lab.model.Paper;
import univ.lab.model.Papers;

import java.util.ArrayList;

public class RegularSAXHandler extends DefaultHandler {
    private Object result = null;
    private Papers currentPapers;
    private Paper currentPaper;
    private Characteristics currentCharacteristics;
    private String bufferedTag;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        switch (qName) {
            case "papers" -> {
                currentPapers = new Papers();
                currentPapers.setPapersList(new ArrayList<>());
            }
            case "paper" -> {
                currentPaper = new Paper();
                long id = Integer.parseInt(attributes.getValue("id"));
                currentPaper.setId(id);
            }
            case "characteristics" -> currentCharacteristics = new Characteristics();
        }
    }
    @Override
    public void characters(char[] chars, int start, int length) {
        bufferedTag = new String(chars, start, length);
    }
    @Override
    public void endElement(String uri, String localName, String qName) {
        String v = bufferedTag.trim();
        switch (qName) {
            case "papers" -> result = currentPapers;
            case "paper" -> {
                currentPapers.getPapersList().add(currentPaper);
                currentPaper = null;
            }
            case "characteristics" -> {
                currentPaper.setCharacteristics(currentCharacteristics);
                currentCharacteristics = null;
            }
            case "title" -> currentPaper.setTitle(v);
            case "type" -> currentPaper.setType(v);
            case "monthly" -> currentPaper.setMonthly(Boolean.parseBoolean(v));
            case "colored" -> currentCharacteristics.setIsColored(Boolean.parseBoolean(v));
            case "volume" -> currentCharacteristics.setVolume(Integer.parseInt(v));
            case "glossy" -> currentCharacteristics.setIsGlossy(Boolean.parseBoolean(v));
            case "subscription_index" -> currentCharacteristics.setHasSubscriptionIndex(Boolean.parseBoolean(v));
        }

    }

    public Object getResult() {
        return result;
    }
}
