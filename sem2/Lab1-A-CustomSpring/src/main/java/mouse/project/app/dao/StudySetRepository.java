package mouse.project.app.dao;

import mouse.project.app.model.SizedStudySet;
import mouse.project.app.model.StudySet;
import mouse.project.lib.data.page.Page;
import mouse.project.lib.data.page.PageDescription;
import mouse.project.lib.service.repository.CustomCrudRepository;
import mouse.project.lib.service.repository.SoftDeleteCrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudySetRepository extends SoftDeleteCrudRepository<StudySet, Long> {
    List<StudySet> findAllByNameIgnoreCase(String name);
    List<StudySet> findAllByCreatedDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<StudySet> findAllByUserId(Long userId);
    Page<StudySet> findAllByUserId(Long userId, PageDescription pageDescription);
    Optional<StudySet> findAllByIdWithTerms(Long id);
    Integer getTermCount(Long setId);
    Optional<SizedStudySet> findByIdWithSize(Long id);
}
