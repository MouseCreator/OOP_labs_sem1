package univ.lab.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.lab.model.CarInsurance;
import univ.lab.model.HouseInsurance;
import univ.lab.model.Insurance;
import univ.lab.model.LifeInsurance;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceDaoImplTest extends InMemoryDatabaseTest {
    private InsuranceDao insuranceDao;
    @BeforeEach
    public void initInsuranceDao() {
        CrudDaoManager<Insurance> crudDaoManager = new CrudDaoManagerImpl<>(getSessionFactory(), Insurance.class);
        insuranceDao = new InsuranceDaoImpl(crudDaoManager);
    }

    @Test
    void save_Ok() {
        LifeInsurance lifeInsurance = new LifeInsurance();
        lifeInsurance.setRisk(10);
        lifeInsurance.setOwnerName("Petrov");
        lifeInsurance.setRiskFactor("asteroid");

        Insurance actual = insuranceDao.save(lifeInsurance);

        assertNotNull(actual);
        assertEquals(1L, actual.getId());
    }

    @Test
    void get_by_Id_Ok() {
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
    void get_by_Id_Empty() {
        int itemsInjected = injectData();
        Optional<Insurance> byIdOverLimit = insuranceDao.findById(itemsInjected+1L);
        assertTrue(byIdOverLimit.isEmpty());
    }

    private int injectData() {
        LifeInsurance lifeInsurance = new LifeInsurance();
        lifeInsurance.setRisk(10);
        lifeInsurance.setOwnerName("Petrov");
        lifeInsurance.setRiskFactor("asteroid");
        insuranceDao.save(lifeInsurance);

        HouseInsurance houseInsurance = new HouseInsurance();
        houseInsurance.setRisk(5);
        houseInsurance.setOwnerName("Ivanov");
        houseInsurance.setHouseAddress("Abc street");
        insuranceDao.save(houseInsurance);

        CarInsurance carInsurance = new CarInsurance();
        houseInsurance.setRisk(2);
        houseInsurance.setOwnerName("Ivanov");
        houseInsurance.setHouseAddress("Abc street");
        insuranceDao.save(houseInsurance);

        return 2;
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Insurance.class, LifeInsurance.class, HouseInsurance.class, CarInsurance.class};
    }
}