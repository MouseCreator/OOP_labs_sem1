package mouse.project.app.mapper;

import mouse.project.app.dto.settag.SetTagResponseDTO;
import mouse.project.app.model.SetTag;
import org.mapstruct.Mapper;
import mouse.project.app.config.MapperConfig;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {TagMapper.class, UserMapper.class, StudySetMapper.class})
public interface SetTagMapper {
    @Mapping(source = "user", target = "userId", qualifiedByName = "userToId")
    @Mapping(source = "studySet", target = "studySetId", qualifiedByName = "studySetToId")
    @Mapping(source = "tag", target = "tagId", qualifiedByName = "tagToId")
    SetTagResponseDTO toResponse(SetTag setTag);
}
