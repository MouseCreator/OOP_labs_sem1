package univ.lab.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDaoManager<T> {
    T save(T entity);
    List<T> findAll();
    Optional<T> findById(Long id);
    void delete(Long id);
    void delete(T entity);
    T update(T entity);

}
