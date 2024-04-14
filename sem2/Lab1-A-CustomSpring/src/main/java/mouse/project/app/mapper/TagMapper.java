package mouse.project.app.mapper;

import mouse.project.app.dto.tag.TagCreateDTO;
import mouse.project.app.dto.tag.TagResponseDTO;
import mouse.project.app.dto.tag.TagUpdateDTO;
import mouse.project.app.model.Tag;
import org.mapstruct.Mapper;
import mouse.project.app.config.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = UserMapper.class)
public interface TagMapper {
    @Mapping(source = "ownerId", target = "owner", qualifiedByName = "userById")
    Tag fromCreate(TagCreateDTO createDTO);
    @Mapping(source = "ownerId", target = "owner", qualifiedByName = "userById")
    Tag fromUpdate(TagUpdateDTO createDTO);
    @Mapping(source = "owner", target = "ownerId", qualifiedByName = "userToId")
    TagResponseDTO toResponse(Tag createDTO);

    @Named("tagById")
    default Tag getUserById(Long id) {
        return id == null ? null : new Tag(id);
    }
    @Named("tagToId")
    default Long getUserId(Tag user) {
        return user == null ? null : user.getId();
    }
}
