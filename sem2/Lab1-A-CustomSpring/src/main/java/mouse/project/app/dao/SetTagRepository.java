package mouse.project.app.dao;

import mouse.project.app.model.SetTag;
import mouse.project.app.model.StudySet;
import mouse.project.lib.service.repository.GenericRepository;

import java.util.List;
import java.util.Optional;

public interface SetTagRepository extends GenericRepository {
    List<SetTag> getAll();
    SetTag save(SetTag setTag);
    Optional<SetTag> getSetTagById(Long userId, Long setId, Long tagId);
    List<StudySet> getStudySetsByUserAndTags(Long userId, List<Long> tagIds);
    void delete(Long userId, Long setId, Long tagId);
}
