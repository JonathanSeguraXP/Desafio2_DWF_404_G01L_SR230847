package sv.edu.udb.UserSubscripcionAPI.mapper;

import sv.edu.udb.UserSubscripcionAPI.dto.UserRequestDto;
import sv.edu.udb.UserSubscripcionAPI.dto.UserResponseDto;
import sv.edu.udb.UserSubscripcionAPI.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    void updateEntityFromDto(UserRequestDto userRequestDto, @MappingTarget User user);
}