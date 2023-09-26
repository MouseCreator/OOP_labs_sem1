package univ.lab.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.lab.factory.DerivativeFactory;
import univ.lab.factory.DerivativeFactoryImpl;
import univ.lab.factory.InsuranceFactory;
import univ.lab.factory.InsuranceFactoryImpl;
import univ.lab.model.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DerivativeDaoImplTest extends InMemoryDatabaseTest {

    private DerivativeDao derivativeDao;
    private DerivativeFactory derivativeFactory;
    private InsuranceFactory insuranceFactory;
    private InsuranceDao insuranceDao;

    @BeforeEach
    void setUp() {
        this.derivativeDao = new DerivativeDaoImpl(new CrudDaoManagerImpl<>(getSessionFactory(), Derivative.class));
        derivativeFactory = new DerivativeFactoryImpl();
        insuranceFactory = new InsuranceFactoryImpl();
        insuranceDao = new InsuranceDaoImpl(new CrudDaoManagerImpl<>(getSessionFactory(), Insurance.class));
    }

    @Test
    void save() {
        Derivative derivative = derivativeFactory.getDerivative();
        Derivative saved = derivativeDao.save(derivative);
        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        assertNotNull(saved.getInsurances());

        Optional<Derivative> getterOptional = derivativeDao.findById(1L);
        assertTrue(getterOptional.isPresent());

        Derivative savedDerivative = getterOptional.get();
        assertNotNull(savedDerivative.getInsurances());
    }

    private int injectData() {

        Insurance insurance1 = insuranceDao.save(insuranceFactory.getLifeInsurance(5, "Alice", 200L, "Ice"));
        Insurance insurance2 = insuranceDao.save(insuranceFactory.getCarInsurance(4, "Bob", 100L, "AA 00 BB", 200L));
        Insurance insurance3 = insuranceDao.save(insuranceFactory.getCarInsurance(3, "Carl", 50L, "CC 00 DD", 170L));
        Derivative derivative1 = derivativeFactory.getDerivative();
        derivative1.getInsurances().add(insurance1);
        derivative1.getInsurances().add(insurance2);
        derivative1.getInsurances().add(insurance3);
        derivativeDao.save(derivative1);

        Insurance insurance4 = insuranceDao.save(insuranceFactory.getHouseInsurance(5, "Alice", 80L, "Vito street"));
        Insurance insurance5 = insuranceDao.save( insuranceFactory.getCarInsurance(2, "Bill", 20L, "EE 00 FF", 30L));

        Derivative derivative2 = derivativeFactory.getDerivative();
        derivative2.getInsurances().add(insurance4);
        derivative2.getInsurances().add(insurance5);
        derivativeDao.save(derivative2);
        Derivative derivative3 = derivativeFactory.getDerivative();
        derivativeDao.save(derivative3);
        return 3;
    }
    @Test
    void findAll() {
        int injectedItemsCount = injectData();
        List<Derivative> derivativeList = derivativeDao.findAll();
        assertNotNull(derivativeList);
        assertEquals(injectedItemsCount, derivativeList.size());
        Long i = 1L;
        for (Derivative derivative : derivativeList) {
            assertNotNull(derivative);
            assertEquals(i, derivative.getId());
            i++;
        }
    }

    @Test
    void findById() {
        int injectedItemsCount = injectData();
        assert injectedItemsCount > 0;
        Optional<Derivative> derivativeOptional = derivativeDao.findById(1L);
        assertTrue(derivativeOptional.isPresent());
        Derivative derivative = derivativeOptional.get();
        assertEquals(1L, derivative.getId());
    }

    @Test
    void delete() {
        int injectedItemsCount = injectData();
        assert injectedItemsCount > 0;
        int initialSize = derivativeDao.findAll().size();
        derivativeDao.delete(1L);
        int actualSize = derivativeDao.findAll().size();
        assertEquals(initialSize - 1L, actualSize);
    }

    @Test
    void update() {
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Derivative.class, Insurance.class, LifeInsurance.class, CarInsurance.class, HouseInsurance.class};
    }
}