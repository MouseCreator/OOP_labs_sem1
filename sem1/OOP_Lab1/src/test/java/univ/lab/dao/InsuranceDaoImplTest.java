package univ.lab.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.lab.factory.InsuranceFactory;
import univ.lab.factory.InsuranceFactoryImpl;
import univ.lab.model.CarInsurance;
import univ.lab.model.HouseInsurance;
import univ.lab.model.Insurance;
import univ.lab.model.LifeInsurance;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceDaoImplTest extends InMemoryDatabaseTest {
    private InsuranceDao insuranceDao;
    private InsuranceFactory insuranceFactory;
    @BeforeEach
    public void initInsuranceDao() {
        CrudDaoManager<Insurance> crudDaoManager = new CrudDaoManagerImpl<>(getSessionFactory(), Insurance.class);
        insuranceDao = new InsuranceDaoImpl(crudDaoManager);

        insuranceFactory = new InsuranceFactoryImpl();
    }

    @Test
    void save_Ok() {
        LifeInsurance lifeInsurance = insuranceFactory.getLifeInsurance(10, "Petrov", 200L, "Asteroid");

        Insurance actual = insuranceDao.save(lifeInsurance);

        assertNotNull(actual);
        assertEquals(1L, actual.getId());
    }

    @Test
    void getByIdOk() {
        int itemsInjected = injectData();

        assert itemsInjected >= 2;

        Optional<Insurance> byId1 = insuranceDao.findById(1L);

        assertNotNull(byId1);
        assertTrue(byId1.isPresent());
        assertEquals(1L, byId1.get().getId());

        Optional<Insurance> byId2 = insuranceDao.findById(2L);

        assertNotNull(byId2);
        assertTrue(byId2.isPresent());
        assertEquals(2L, byId2.get().getId());

        assertNotEquals(byId1, byId2);
    }

    @Test
    void getByIdEmpty() {
        int itemsInjected = injectData();
        Optional<Insurance> byIdOverLimit = insuranceDao.findById(itemsInjected+1L);
        assertTrue(byIdOverLimit.isEmpty());
    }

    @Test
    void getAll() {
        int itemsInjected = injectData();

        List<Insurance> items = insuranceDao.findAll();


        assertNotNull(items);

        assertEquals(itemsInjected, items.size());

        for (Insurance insurance : items) {
            assertNotNull(insurance);
        }
    }

    @Test
    void deleteById() {
        int itemsInjected = injectData();
        assert itemsInjected > 0;
        int sizeBefore = insuranceDao.findAll().size();

        insuranceDao.delete(1L);

        int sizeAfter = insuranceDao.findAll().size();

        assertEquals(1L, sizeBefore - sizeAfter);
    }

    @Test
    void update() {
        int itemsInjected = injectData();
        assert itemsInjected > 0;

        Optional<Insurance> insuranceOptional = insuranceDao.findById(1L);
        assertTrue(insuranceOptional.isPresent());

        Insurance insurance = insuranceOptional.get();
        Long price = insurance.getPrice();
        Long increasedPrice = price + 100L;
        insurance.setPrice(increasedPrice);

        insuranceDao.update(insurance);

        Optional<Insurance> updatedOptional = insuranceDao.findById(1L);
        assertTrue(updatedOptional.isPresent());

        Insurance updated = insuranceOptional.get();
        assertEquals(increasedPrice, updated.getPrice());
    }

    private int injectData() {
        LifeInsurance lifeInsurance = insuranceFactory.getLifeInsurance(10, "Petrov", 100L, "Asteroid");
        insuranceDao.save(lifeInsurance);

        HouseInsurance houseInsurance = insuranceFactory.getHouseInsurance(5, "Ivanov", 50L, "Abc street");
        insuranceDao.save(houseInsurance);

        CarInsurance carInsurance = insuranceFactory.getCarInsurance(3, "Sidney", 10L, "AA 0000 BB",10L);
        insuranceDao.save(carInsurance);

        return 3;
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Insurance.class, LifeInsurance.class, HouseInsurance.class, CarInsurance.class};
    }
}