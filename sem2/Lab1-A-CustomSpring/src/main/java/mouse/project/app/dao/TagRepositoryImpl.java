package mouse.project.app.dao;

import mouse.project.app.model.Tag;
import mouse.project.app.model.User;
import mouse.project.lib.data.executor.Executor;

import java.util.List;
import java.util.Optional;

public class TagRepositoryImpl implements TagRepository {
    private final Executor executor;

    public TagRepositoryImpl(Executor executor) {
        this.executor = executor;
    }

    @Override
    public List<Tag> findAll() {
        return executor.executeQuery("SELECT * FROM tags t WHERE t.deleted_at IS NULL").
                adjustedList(Tag.class).apply(this::addUser).get();
    }

    private void addUser(Tag tag) {
        Long id = tag.getId();
        Optional<User> optional = executor.executeQuery(
                "SELECT u.* " +
                    "FROM users u " +
                    "INNER JOIN tags t ON u.id = t.user_id " +
                    "WHERE t.id = ?", id
        ).optional(User.class);
        optional.ifPresent(tag::setOwner);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return executor.executeQuery("SELECT * FROM tags t WHERE t.id = ? AND t.deletedAt IS NULL", id)
                .adjustedOptional(Tag.class).apply(this::addUser).get();
    }

    @Override
    public void deleteById(Long id) {
        executor.executeQuery("UPDATE tags t SET deleted_at = NOW() WHERE t.id = ?", id);
    }

    @Override
    public Tag save(Tag model) {
        return executor.executeQuery(
                "INSERT INTO tags (name, color, owner, deleted_at) VALUES (?, ?, ?, ?)",
                model.getName(), model.getColorHex(), model.getOwner(), null
        ).adjusted(Tag.class).apply(this::addUser).get();
    }

    @Override
    public List<Tag> findAllIncludeDeleted() {
        return executor.executeQuery("SELECT * FROM tags").adjustedList(Tag.class).apply(this::addUser).get();
    }

    @Override
    public void restoreById(Long id) {
        executor.executeQuery("UPDATE tags t SET deleted_at = NULL WHERE t.id = ?", id);
    }

    @Override
    public Optional<Tag> findByIdIncludeDeleted(Long id) {
        return executor.executeQuery("SELECT * FROM tags t WHERE t.id = ?")
                .adjustedOptional(Tag.class)
                .apply(this::addUser)
                .get();
    }

    @Override
    public List<Tag> getTagsByOwner(Long ownerId) {
        return executor.executeQuery(
                "SELECT t.* FROM tags t " +
                    "INNER JOIN users u ON t.owner_id = u.id " +
                    "WHERE t.owner_id = ? " +
                    "AND t.deleted_at IS NULL AND u.deleted_at IS NULL", ownerId
        ).adjustedList(Tag.class).apply(this::addUser).get();
    }

    @Override
    public List<Tag> getTagsByOwnerAndName(Long ownerId, String name) {
        return executor.executeQuery(
                "SELECT t.* FROM tags t " +
                    "INNER JOIN users u ON t.owner_id = u.id " +
                    "WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', ?, '%')) " +
                    "AND u.id = ? " +
                    "AND u.deletedAt IS NULL " +
                    "AND t.deletedAt IS NULL",
        name, ownerId).adjustedList(Tag.class).apply(this::addUser).get();
    }
}
