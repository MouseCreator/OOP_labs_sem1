package univ.lab.service;

import univ.lab.dao.InsuranceDao;
import univ.lab.model.Insurance;

public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceDao insuranceDao;
    public InsuranceServiceImpl(InsuranceDao insuranceDao) {
        this.insuranceDao = insuranceDao;
    }

    @Override
    public Insurance save(Insurance insurance) {
        return insuranceDao.save(insurance);
    }
}
