package univ.lab.dtwtest.dtw;

import junit.framework.TestCase;

import org.junit.Test;

public class IntegerDistanceCalculatorTest extends TestCase {
    private DistanceCalculator<Integer> calculator;


    @Test
    public void testCalculate() {
        calculator = new IntegerDistanceCalculator();
        approximateEqual(3, calculator.calculate(1,4));
        approximateEqual(3, calculator.calculate(4,1));
        approximateEqual(0, calculator.calculate(1,1));
        approximateEqual(0, calculator.calculate(0,0));
    }

    private void approximateEqual(double d1, double d2) {
        assertTrue(Math.abs(d1 - d2) < 0.001);
    }
}