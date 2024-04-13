package mouse.project.app.dao;

import mouse.project.app.model.Term;

import java.util.List;
import java.util.Optional;

public interface TermRepository {
    List<Term> findAll();
    List<Term> findAllIncludeDeleted();
    Optional<Term> findById(Long id);
    Optional<Term> findByIdIncludeDeleted(Long id);
    void deleteById(Long id);
    void restoreById(Long id);
    Term save(Term  model);
    void removeTermFormStudySetsById(Long termId);
    List<Term> findAllByIds( List<Long> termIds);
}
