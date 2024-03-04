package univ.lab.validator;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXParseException;

import static org.junit.jupiter.api.Assertions.*;

class XSDValidatorTest {

    @Test
    void isValid() {
        XSDValidator validator = new XSDValidator();
        String xsdApply = "src/test/resources/test-paper.xsd";
        String xsdWrong = "src/test/resources/test-paper-2.xsd";
        String xml = "src/test/resources/test-paper.xml";
        assertTrue(validator.isValid(xsdApply, xml));
        assertFalse(validator.isValid(xsdWrong, xml));
    }

    @Test
    void validate() {
        XSDValidator validator = new XSDValidator();
        String xsdApply = "src/test/resources/test-paper.xsd";
        String xsdWrong = "src/test/resources/test-paper-2.xsd";
        String xml = "src/test/resources/test-paper.xml";
        assertDoesNotThrow(() -> validator.validate(xsdApply, xml));
        assertThrows(SAXParseException.class, ()->validator.validate(xsdWrong, xml));
    }
}