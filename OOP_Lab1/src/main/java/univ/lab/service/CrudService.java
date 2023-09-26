package univ.lab.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
    T save(T entity);
    Optional<T> find(Long id);
    List<T> findAll();
    void delete(T entity);
    void delete(Long id);
    T update(T entity);
}
