package univ.lab.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.lab.model.Derivative;

import static org.junit.jupiter.api.Assertions.*;

class DerivativeFactoryImplTest {
    DerivativeFactory derivativeFactory = null;

    @BeforeEach
    public void setUp() {
        derivativeFactory = new DerivativeFactoryImpl();
    }

    @Test
    void getDerivative() {
        Derivative derivative = derivativeFactory.getDerivative();
        assertNotNull(derivative);
        assertNotNull(derivative.getInsurances());
        assertTrue(derivative.getInsurances().isEmpty());
        assertNull(derivative.getId());
    }
}