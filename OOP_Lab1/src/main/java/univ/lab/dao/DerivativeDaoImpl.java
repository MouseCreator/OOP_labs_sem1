package univ.lab.dao;

import univ.lab.model.Derivative;

import java.util.List;
import java.util.Optional;

public class DerivativeDaoImpl implements DerivativeDao {

    private final CrudDaoManager<Derivative> crudDaoManager;

    public DerivativeDaoImpl(CrudDaoManager<Derivative> crudDaoManager) {
        this.crudDaoManager = crudDaoManager;
    }

    @Override
    public Derivative save(Derivative insurance) {
        return crudDaoManager.save(insurance);
    }

    @Override
    public List<Derivative> findAll() {
        return crudDaoManager.findAll();
    }

    @Override
    public Optional<Derivative> findById(Long id) {
        return crudDaoManager.findById(id);
    }

    @Override
    public void delete(Long id) {
        crudDaoManager.delete(id);
    }

    @Override
    public void delete(Derivative entity) {
        crudDaoManager.delete(entity);
    }

    @Override
    public Derivative update(Derivative entity) {
        return crudDaoManager.update(entity);
    }
}
