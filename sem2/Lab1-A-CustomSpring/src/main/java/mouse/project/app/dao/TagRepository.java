package mouse.project.app.dao;

import mouse.project.app.model.Tag;
import mouse.project.lib.service.repository.SoftDeleteCrudRepository;

import java.util.List;

public interface TagRepository extends SoftDeleteCrudRepository<Tag, Long> {
    List<Tag> getTagsByOwner(Long ownerId);
    List<Tag> getTagsByOwnerAndName(Long ownerId, String name);
}
