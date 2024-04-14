package mouse.project.app.dao;

import mouse.project.app.model.SetTag;
import mouse.project.app.model.StudySet;
import mouse.project.lib.data.executor.Executor;
import mouse.project.lib.data.utils.DaoUtils;
import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Dao
public class SetTagRepositoryImpl implements SetTagRepository {
    private final Executor executor;
    private final DaoUtils daoUtils;
    @Auto
    public SetTagRepositoryImpl(Executor executor, DaoUtils daoUtils) {
        this.executor = executor;
        this.daoUtils = daoUtils;
    }

    @Override
    public List<SetTag> getAll() {
        return executor.executeQuery("SELECT * FROM set_tags").list(SetTag.class);
    }

    @Override
    public SetTag save(SetTag setTag) {
        return executor.executeQuery("INSERT INTO set_tags (tag_id, set_id, tag_id) VALUES (?, ?, ?)",
                setTag.getTag(),
                setTag.getStudySet(),
                setTag.getUser()).model(SetTag.class);
    }

    @Override
    public Optional<SetTag> getSetTagById(Long userId, Long setId, Long tagId) {
        return executor.executeQuery(
                "SELECT * FROM set_tags st " +
                    "INNER JOIN sets s ON s.id = st.set_id " +
                    "INNER JOIN tags t ON t.id = st.tag_id " +
                    "INNER JOIN users u ON u.id = st.user_id " +
                    "WHERE s.id = ? AND t.id = ? AND u.id = ? AND " +
                    "s.deleted_at IS NULL AND t.deleted_at IS NULL AND u.deleted_at IS NULL",
                setId, tagId, userId
        ).optional(SetTag.class);
    }

    @Override
    public List<StudySet> getStudySetsByUserAndTags(Long userId, List<Long> tagIds) {
        String qMarksList = daoUtils.qMarksList(tagIds);
        List<Long> arguments = new ArrayList<>(tagIds);
        arguments.add(0, userId);
        String sql = String.format(
            "SELECT * " +
            "FROM study_sets s " +
            "INNER JOIN set_tags st ON st.set_id = s.id " +
            "INNER JOIN users u ON st.user_id = u.id " +
            "INNER JOIN tags y ON st.tag_id = y.id " +
            "WHERE u.id = ? AND " +
            "y.deleted_at IS NULL AND u.deleted_at IS NULL AND s.deleted_at IS NULL " +
            "AND NOT EXISTS ( " +
                "SELECT t " +
                "FROM tags t " +
                "WHERE t.id IN %s AND t.id NOT IN (" +
                    "SELECT r.tag_id " +
                    "FROM set_tags r " +
                    "WHERE r.set_id = st.set_id))", qMarksList
        );
        return executor.executeQuery(
          sql, arguments
        ).list(StudySet.class);
    }

    @Override
    public void delete(Long userId, Long setId, Long tagId) {
        executor.executeQuery(
                "DELETE FROM set_tags st WHERE st.user_id = ? AND st.set_id = ? AND st.tag_id = ?",
                userId, setId, tagId);
    }
}
