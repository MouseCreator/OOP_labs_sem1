package mouse.project.app.dao;

import mouse.project.app.model.Term;
import mouse.project.lib.service.repository.SoftDeleteCrudRepository;

import java.util.List;
import java.util.Optional;

public interface TermRepository extends SoftDeleteCrudRepository<Term, Long> {
    List<Term> findAllByStudySet(Long setId);
    void removeTermFormStudySetsById(Long termId);
    List<Term> findAllByIds(List<Long> termIds);
}
