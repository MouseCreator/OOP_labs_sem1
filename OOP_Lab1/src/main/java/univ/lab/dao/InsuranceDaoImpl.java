package univ.lab.dao;

import univ.lab.model.Insurance;

import java.util.List;
import java.util.Optional;

public class InsuranceDaoImpl implements InsuranceDao {

    private final CrudDaoManager<Insurance> crudDaoManager;

    public InsuranceDaoImpl(CrudDaoManager<Insurance> crudDaoManager) {
        this.crudDaoManager = crudDaoManager;
    }

    @Override
    public Insurance save(Insurance insurance) {
        return crudDaoManager.save(insurance);
    }

    @Override
    public List<Insurance> findAll() {
        return crudDaoManager.findAll();
    }

    @Override
    public Optional<Insurance> findById(Long id) {
        return crudDaoManager.findById(id);
    }

    @Override
    public void delete(Long id) {
        crudDaoManager.delete(id);
    }

    @Override
    public Insurance update(Insurance entity) {
        return crudDaoManager.update(entity);
    }
}
