package mouse.project.app.dao;

import mouse.project.app.model.StudySet;
import mouse.project.lib.data.page.Page;
import mouse.project.lib.data.page.PageDescription;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudySetRepository {
    List<StudySet> findAll();
    List<StudySet> findAllIncludeDeleted();
    List<StudySet> findAllByNameIgnoreCase(String name);
    List<StudySet> findAllByCreatedDateRange(LocalDateTime startDate, LocalDateTime endDate);
    void deleteById(Long id);
    void restoreById(Long id);
    StudySet save(StudySet model);
    Optional<StudySet> findById(Long id);
    Optional<StudySet> findByIdIncludeDeleted(Long id);
    List<StudySet> findAllByUserId(Long userId);
    Page<StudySet> findAllByUserId(Long userId, PageDescription pageDescription);
    Optional<StudySet> findAllByIdWithTerms(Long id);
    Integer getTermCount(Long setId);
}
