package mouse.project.app.service;

import mouse.project.app.dto.user.UserCreateDTO;
import mouse.project.app.dto.user.UserResponseDTO;
import mouse.project.app.dto.user.UserUpdateDTO;

import java.util.List;


public interface UserService {
    UserResponseDTO save(UserCreateDTO model);
    List<UserResponseDTO> findAll();
    UserResponseDTO getById(Long id);
    UserResponseDTO update(UserUpdateDTO model);
    void removeById(Long id);
    UserResponseDTO hardGet(Long id);
    void restoreById(Long id);
    List<UserResponseDTO> findAllWithDeleted();
    List<UserResponseDTO> findByName(String name);
}
