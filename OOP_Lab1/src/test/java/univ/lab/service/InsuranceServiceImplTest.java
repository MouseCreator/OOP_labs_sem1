package univ.lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import univ.lab.dao.InsuranceDao;
import univ.lab.model.Insurance;
import univ.lab.model.InsuranceOwner;
import univ.lab.model.LifeInsurance;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceServiceImplTest {
    private InsuranceService insuranceService;
    @BeforeEach
    void setUp() {
        InsuranceDao insuranceDaoMock = Mockito.mock(InsuranceDao.class);
        insuranceService = new InsuranceServiceImpl(insuranceDaoMock);
    }
    @Test
    void saveInsurance_Ok() {
        LifeInsurance lifeInsurance = new LifeInsurance();
        lifeInsurance.setRiskFactor("asteroid");
        lifeInsurance.setRisk(10);
        lifeInsurance.setOwner(new InsuranceOwner());

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