package univ.lab.service;

import univ.lab.dao.InsuranceDao;
import univ.lab.model.Insurance;

import java.util.List;
import java.util.Optional;

public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceDao insuranceDao;
    public InsuranceServiceImpl(InsuranceDao insuranceDao) {
        this.insuranceDao = insuranceDao;
    }
    @Override
    public Insurance save(Insurance insurance) {
        return insuranceDao.save(insurance);
    }

    @Override
    public Optional<Insurance> find(Long id) {
        return insuranceDao.findById(id);
    }

    @Override
    public List<Insurance> findAll() {
        return insuranceDao.findAll();
    }

    @Override
    public void delete(Insurance entity) {
        insuranceDao.delete(entity);
    }

    @Override
    public void delete(Long id) {
        insuranceDao.delete(id);
    }

    @Override
    public Insurance update(Insurance entity) {
        return insuranceDao.update(entity);
    }
}
