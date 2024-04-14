package mouse.project.app.mapper;

import mouse.project.app.dto.user.UserCreateDTO;
import mouse.project.app.dto.user.UserResponseDTO;
import mouse.project.app.dto.user.UserUpdateDTO;
import mouse.project.app.model.User;
import org.mapstruct.Mapper;
import mouse.project.app.config.MapperConfig;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface UserMapper  {
    UserResponseDTO toResponse(User model);
    User fromCreate(UserCreateDTO createDTO);
    User fromUpdate(UserUpdateDTO updateDTO);
    @Named("userById")
    default User getUserById(Long id) {
        return id == null ? null : new User(id);
    }
    @Named("userToId")
    default Long getUserId(User user) {
        return user == null ? null : user.getId();
    }
}
