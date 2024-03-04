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
        Long price = 200L;
        LifeInsurance lifeInsurance = insuranceFactory.getLifeInsurance(risk, owner, price, factor);

        assertEquals(risk, lifeInsurance.getRisk());
        assertEquals(owner, lifeInsurance.getOwnerName());
        assertEquals(price, lifeInsurance.getPrice());
        assertEquals(factor, lifeInsurance.getRiskFactor());

        assertNull(lifeInsurance.getId());
    }

    @Test
    void getLifeInsurance_Not_Null() {
        Integer risk = 10;
        String owner = "Petrov";
        String factor = "Killer";
        Long price = 200L;
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getLifeInsurance(null, owner, price, factor));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getLifeInsurance(risk, null, price, factor));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getLifeInsurance(risk, owner,  null, factor));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getLifeInsurance(risk, owner, price, null));
    }

    @Test
    void getCarInsurance_Ok() {
        Integer risk = 10;
        String owner = "Petrov";
        String number = "AA 0000 AA";
        Long price = 2000L;
        Long carPrice = 20_000L;
        CarInsurance lifeInsurance = insuranceFactory.getCarInsurance(risk, owner, price, number, carPrice);

        assertEquals(risk, lifeInsurance.getRisk());
        assertEquals(owner, lifeInsurance.getOwnerName());
        assertEquals(price, lifeInsurance.getPrice());
        assertEquals(number, lifeInsurance.getCarNumber());
        assertEquals(carPrice, lifeInsurance.getCarPriceDollars());

        assertNull(lifeInsurance.getId());
    }

    @Test
    void getCarInsurance_Not_Null() {
        Integer risk = 10;
        String owner = "Petrov";
        String number = "AA 0000 AA";
        Long price = 10_000L;
        Long carPrice = 20_000L;
        assertThrows(IllegalArgumentException.class, ()->
                insuranceFactory.getCarInsurance(null, owner, price, number, carPrice));
        assertThrows(IllegalArgumentException.class, ()->
                insuranceFactory.getCarInsurance(risk, null, price, number, carPrice));
        assertThrows(IllegalArgumentException.class, ()->
                insuranceFactory.getCarInsurance(risk, owner, null, number, carPrice));
        assertThrows(IllegalArgumentException.class, ()->
                insuranceFactory.getCarInsurance(risk, owner, price, null, carPrice));
        assertThrows(IllegalArgumentException.class, ()->
                insuranceFactory.getCarInsurance(risk, owner, price,  number, null));
    }

    @Test
    void getHouseInsurance_Ok() {
        Integer risk = 10;
        String owner = "Petrov";
        String address = "ABC street";
        Long price = 200L;
        HouseInsurance lifeInsurance = insuranceFactory.getHouseInsurance(risk, owner, price, address);

        assertEquals(risk, lifeInsurance.getRisk());
        assertEquals(owner, lifeInsurance.getOwnerName());
        assertEquals(address, lifeInsurance.getHouseAddress());
        assertEquals(price, lifeInsurance.getPrice());

        assertNull(lifeInsurance.getId());
    }

    @Test
    void getHouseInsurance_Not_Null() {
        Integer risk = 10;
        String owner = "Petrov";
        String address = "ABC street";
        Long price = 230L;
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getHouseInsurance(null, owner, price, address));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getHouseInsurance(risk, null, price,  address));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getHouseInsurance(risk, owner, null, address));
        assertThrows(IllegalArgumentException.class, ()-> insuranceFactory.getHouseInsurance(risk, owner, price, null));
    }
}