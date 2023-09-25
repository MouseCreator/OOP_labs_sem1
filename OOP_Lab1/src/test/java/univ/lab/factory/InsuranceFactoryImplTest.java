package univ.lab.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.lab.model.CarInsurance;
import univ.lab.model.HouseInsurance;
import univ.lab.model.LifeInsurance;

import static org.junit.jupiter.api.Assertions.*;
class InsuranceFactoryImplTest {
    InsuranceFactory insuranceFactory = null;

    @BeforeEach
    public void setUp() {
        insuranceFactory = new InsuranceFactoryImpl();
    }

    @Test
    void getLifeInsurance_Ok() {
        Integer risk = 10;
        String owner = "Petrov";
        String factor = "Killer";
        LifeInsurance lifeInsurance = insuranceFactory.getLifeInsurance(risk, owner, factor);

        assertEquals(risk, lifeInsurance.getRisk());
        assertEquals(owner, lifeInsurance.getOwnerName());
        assertEquals(factor, lifeInsurance.getRiskFactor());

        assertNull(lifeInsurance.getId());
    }

    @Test
    void getLifeInsurance_Not_Null() {
        Integer risk = 10;
        String owner = "Petrov";
        String factor = "Killer";
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getLifeInsurance(null, owner, factor));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getLifeInsurance(risk, null, factor));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getLifeInsurance(risk, owner, null));
    }

    @Test
    void getCarInsurance_Ok() {
        Integer risk = 10;
        String owner = "Petrov";
        String number = "AA 0000 AA";
        Long price = 20_000L;
        CarInsurance lifeInsurance = insuranceFactory.getCarInsurance(risk, owner, number, price);

        assertEquals(risk, lifeInsurance.getRisk());
        assertEquals(owner, lifeInsurance.getOwnerName());
        assertEquals(number, lifeInsurance.getCarNumber());
        assertEquals(price, lifeInsurance.getCarPriceDollars());

        assertNull(lifeInsurance.getId());
    }

    @Test
    void getCarInsurance_Not_Null() {
        Integer risk = 10;
        String owner = "Petrov";
        String number = "AA 0000 AA";
        Long price = 20_000L;
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getCarInsurance(null, owner, number, price));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getCarInsurance(risk, null, number, price));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getCarInsurance(risk, owner, null, price));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getCarInsurance(risk, owner, number, null));
    }

    @Test
    void getHouseInsurance_Ok() {
        Integer risk = 10;
        String owner = "Petrov";
        String address = "ABC street";
        HouseInsurance lifeInsurance = insuranceFactory.getHouseInsurance(risk, owner, address);

        assertEquals(risk, lifeInsurance.getRisk());
        assertEquals(owner, lifeInsurance.getOwnerName());
        assertEquals(address, lifeInsurance.getHouseAddress());

        assertNull(lifeInsurance.getId());
    }

    @Test
    void getHouseInsurance_Not_Null() {
        Integer risk = 10;
        String owner = "Petrov";
        String address = "ABC street";
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getHouseInsurance(null, owner, address));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getHouseInsurance(risk, null, address));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getHouseInsurance(risk, owner, null));
    }
}