package sv.edu.udb.UserSubscripcionAPI.service;

import sv.edu.udb.UserSubscripcionAPI.dto.UserRequestDto;
import sv.edu.udb.UserSubscripcionAPI.dto.UserResponseDto;
import sv.edu.udb.UserSubscripcionAPI.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
    UserResponseDto getUserByEmail(String email);
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
    void deleteUser(Long id);
    boolean existsByEmail(String email);
    User getEntityById(Long id);
}