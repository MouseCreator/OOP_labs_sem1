package mouse.project.app.dao;

import mouse.project.app.model.UserTerm;
import mouse.project.lib.service.repository.GenericRepository;

import java.util.List;

public interface UserTermRepository extends GenericRepository {
    List<UserTerm> findAll();
    void deleteByUserAndTerms(Long id, List<Long> termIds);
    UserTerm save(UserTerm model);
    List<UserTerm> findByUserAndTerms(Long userId, List<Long> terms);
}
