package mouse.project.app.dao;

import mouse.project.app.model.User;
import mouse.project.lib.service.repository.SoftDeleteCrudRepository;

import java.util.List;

public interface UserRepository extends SoftDeleteCrudRepository<User, Long> {

    List<User> findAllByNameIgnoreCase(String name);


}

