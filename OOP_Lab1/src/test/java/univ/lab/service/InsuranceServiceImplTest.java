package univ.lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import univ.lab.dao.InsuranceDao;
import univ.lab.factory.InsuranceFactory;
import univ.lab.factory.InsuranceFactoryImpl;
import univ.lab.model.Insurance;
import univ.lab.model.LifeInsurance;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceServiceImplTest {
    private InsuranceService insuranceService;
    private InsuranceFactory insuranceFactory;
    @BeforeEach
    void setUp() {
        InsuranceDao insuranceDaoMock = Mockito.mock(InsuranceDao.class);
        insuranceService = new InsuranceServiceImpl(insuranceDaoMock);
        insuranceFactory = new InsuranceFactoryImpl();
    }
    @Test
    void saveInsurance_Ok() {
        LifeInsurance lifeInsurance = insuranceFactory.getLifeInsurance(10, "Petrov", 20L, "Asteroid");

        Mockito.when(insuranceService.save(lifeInsurance)).thenReturn(setId(lifeInsurance, 1L));

        Insurance savedInsurance = insuranceService.save(lifeInsurance);

        assertNotNull(savedInsurance);
        assertNotNull(savedInsurance.getId());
        assertEquals(lifeInsurance.getRisk(), savedInsurance.getRisk());
    }

    private Insurance setId(LifeInsurance lifeInsurance, Long id) {
        lifeInsurance.setId(id);
        return lifeInsurance;
    }
}