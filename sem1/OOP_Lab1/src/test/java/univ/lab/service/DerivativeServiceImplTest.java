package univ.lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import univ.lab.dao.DerivativeDao;
import univ.lab.factory.DerivativeFactory;
import univ.lab.factory.DerivativeFactoryImpl;
import univ.lab.factory.InsuranceFactory;
import univ.lab.factory.InsuranceFactoryImpl;
import univ.lab.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class DerivativeServiceImplTest {

    private DerivativeService derivativeService;
    private InsuranceFactory insuranceFactory;
    private DerivativeFactory derivativeFactory;
    @BeforeEach
    void setUp() {
        DerivativeDao insuranceDaoMock = Mockito.mock(DerivativeDao.class);
        derivativeService = new DerivativeServiceImpl(insuranceDaoMock);
        insuranceFactory = new InsuranceFactoryImpl();
        derivativeFactory = new DerivativeFactoryImpl();
    }

    @Test
    void addInsuranceToDerivative() {
        Derivative derivative = derivativeFactory.getDerivative();
        Mockito.when(derivativeService.find(1L)).thenReturn(Optional.of(derivative));

        LifeInsurance lifeInsurance = insuranceFactory.getLifeInsurance(10, "Petrov", 200L, "Fire");
        derivativeService.addInsuranceToDerivative(1L, lifeInsurance);

        assertEquals(1, derivative.getInsurances().size());
    }

    @Test
    void removeInsuranceFromDerivative() {
        Derivative derivative = derivativeFactory.getDerivative();
        Mockito.when(derivativeService.find(1L)).thenReturn(Optional.of(derivative));

        LifeInsurance lifeInsurance = insuranceFactory.getLifeInsurance(10, "Petrov", 200L, "Fire");
        derivativeService.addInsuranceToDerivative(1L, lifeInsurance);

        derivativeService.removeInsuranceFromDerivative(1L, lifeInsurance);

        assertEquals(0, derivative.getInsurances().size());
    }

    private int injectData() {
        Derivative derivative = derivativeFactory.getDerivative();
        Mockito.when(derivativeService.find(1L)).thenReturn(Optional.of(derivative));

        LifeInsurance lifeInsurance = insuranceFactory.getLifeInsurance(10, "Petrov", 200L, "Fire");
        derivativeService.addInsuranceToDerivative(1L, lifeInsurance);

        CarInsurance carInsurance = insuranceFactory.getCarInsurance(3, "Ivanov", 50L, "00 AA 00", 300L);
        derivativeService.addInsuranceToDerivative(1L, carInsurance);

        HouseInsurance houseInsurance = insuranceFactory.getHouseInsurance(5, "Bob", 100L, "ABC street");
        derivativeService.addInsuranceToDerivative(1L, houseInsurance);



        return 3;
    }

    @Test
    void calculatePrice() {
        injectData();

        Long price = derivativeService.calculatePrice(1L);

        assertEquals(350L, price);
    }

    @Test
    void getInsurancesSorted() {
        int injectedCount = injectData();

        List<Insurance> insurancesSorted = derivativeService.getInsurancesSorted(1L);

        assertEquals(injectedCount, insurancesSorted.size());
        assertEquals(10, insurancesSorted.get(0).getRisk());
        assertEquals(5, insurancesSorted.get(1).getRisk());
        assertEquals(3, insurancesSorted.get(2).getRisk());
    }

    @Test
    void getByParams() {
        injectData();
        ArrayList<Predicate<Insurance>> predicates = new ArrayList<>();
        predicates.add(insurance -> insurance.getOwnerName().equals("Bob"));
        List<Insurance> insurancesFiltered = derivativeService.getInsurancesByParameters(1L, predicates);

        assertEquals(1, insurancesFiltered.size());
        assertEquals("Bob", insurancesFiltered.get(0).getOwnerName());
    }
}