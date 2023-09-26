package univ.lab.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.lab.factory.DerivativeFactory;
import univ.lab.factory.DerivativeFactoryImpl;
import univ.lab.model.Derivative;
import univ.lab.model.Insurance;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DerivativeDaoImplTest extends InMemoryDatabaseTest {

    private DerivativeDao derivativeDao;
    private DerivativeFactory derivativeFactory;

    @BeforeEach
    void setUp() {
        this.derivativeDao = new DerivativeDaoImpl(new CrudDaoManagerImpl<>(getSessionFactory(), Derivative.class));
        derivativeFactory = new DerivativeFactoryImpl();
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

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void delete() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void update() {
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Derivative.class, Insurance.class};
    }
}