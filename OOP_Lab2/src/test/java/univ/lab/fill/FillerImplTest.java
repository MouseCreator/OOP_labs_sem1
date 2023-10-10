package univ.lab.fill;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FillerImplTest {
    private static class ToFill {
        @Fill(attribute = "string")
        private String string;
        @Fill(attribute = "bool")
        private Boolean bool;
        private Integer notInjectInt;
    }
    @Test
    void fill_Ok() {
        ToFill toFill = new ToFill();
        toFill.string="";
        toFill.bool=false;
        toFill.notInjectInt=10;

        FillerImpl filler = new FillerImpl();

        String toSetString = "Hello";
        Boolean toSetBool = true;

        filler.fill(toFill, "string", toSetString);
        filler.fill(toFill, "bool", toSetBool);

        assertEquals(toSetString, toFill.string);
        assertEquals(toSetBool, toFill.bool);
    }

    @Test
    void fill_WrongType() {
        ToFill toFill = new ToFill();
        FillerImpl filler = new FillerImpl();
        assertThrows(IllegalArgumentException.class, () -> filler.fill(toFill, "string", true));
    }

    @Test
    void fill_NoSuchAnnotation() {
        ToFill toFill = new ToFill();
        FillerImpl filler = new FillerImpl();
        filler.fill(toFill, "integer", 10);
        assertNull(toFill.notInjectInt);
    }
}