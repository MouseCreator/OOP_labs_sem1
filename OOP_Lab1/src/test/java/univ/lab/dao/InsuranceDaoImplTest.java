package univ.lab.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.lab.model.Insurance;
import univ.lab.model.LifeInsurance;

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

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Insurance.class, LifeInsurance.class};
    }
}