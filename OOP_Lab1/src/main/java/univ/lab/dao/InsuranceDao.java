package univ.lab.dao;

import univ.lab.model.Insurance;

public interface InsuranceDao {
    Insurance save(Insurance insurance);
    Insurance get(Long id);
}
