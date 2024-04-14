package mouse.project.app.dao;

import mouse.project.app.model.UserStudySet;
import mouse.project.lib.service.repository.GenericRepository;

import java.util.List;
import java.util.Optional;

public interface UserStudySetRepository extends GenericRepository {
    List<UserStudySet> findAll();
    Optional<UserStudySet> findByUserAndStudySet(Long user, Long studySetId);
    void deleteByUserAndStudySet(Long userId, Long setId);
    UserStudySet save(UserStudySet model);
    List<UserStudySet> findByUserAndType(Long userId, String type);
    List<UserStudySet> findByUser(Long userId);
}
