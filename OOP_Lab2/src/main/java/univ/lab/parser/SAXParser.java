package univ.lab.parser;

import org.xml.sax.helpers.DefaultHandler;
import univ.lab.model.Paper;

import java.util.List;

public class SAXParser extends DefaultHandler implements Parser<Paper> {
    @Override
    public List<Paper> parse() {
        return null;
    }

    @Override
    public void write(List<Paper> instances) {

    }
}
