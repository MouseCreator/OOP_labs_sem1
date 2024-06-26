package mouse.project.app.dao;

import mouse.project.app.model.User;
import mouse.project.lib.data.executor.Executor;
import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Dao;

import java.util.List;
import java.util.Optional;
@Dao
public class UserRepositoryImpl implements UserRepository {
    private final Executor executor;
    @Auto
    public UserRepositoryImpl(Executor executor) {
        this.executor = executor;
    }

    @Override
    public List<User> findAll() {
        return executor.executeQuery("SELECT * FROM users u WHERE u.deleted_at IS NULL")
                .list(User.class);
    }

    @Override
    public Optional<User> findById(Long id) {
        return executor.executeQuery( "SELECT * FROM users u WHERE u.id = ? AND u.deleted_at IS NULL", id)
                .optional(User.class);
    }

    @Override
    public void deleteById(Long id) {
        executor.executeQuery("UPDATE users u SET deleted_at = NOW() WHERE u.id = ?", id);
    }

    @Override
    public User save(User model) {
        return executor.executeQuery("INSERT INTO users (name, picture_url, deleted_at) VALUES (?, ?, ?)",
                model.getName(), model.getProfilePictureUrl(), null).model(User.class);
    }

    @Override
    public List<User> findAllIncludeDeleted() {
        return executor.executeQuery("SELECT * FROM users").list(User.class);
    }

    @Override
    public void restoreById(Long id) {
        executor.executeQuery("UPDATE users u SET deleted_at = NULL WHERE u.id = ?", id);
    }

    @Override
    public Optional<User> findByIdIncludeDeleted(Long id) {
        return executor.executeQuery( "SELECT * FROM users u WHERE u.id = ?", id)
                .optional(User.class);
    }

    @Override
    public List<User> findAllByNameIgnoreCase(String name) {
        return executor.executeQuery(
                    "SELECT u FROM users u " +
                        "WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', ?, '%')) " +
                        "AND u.deletedAt IS NULL")
                .list(User.class);
    }
}
