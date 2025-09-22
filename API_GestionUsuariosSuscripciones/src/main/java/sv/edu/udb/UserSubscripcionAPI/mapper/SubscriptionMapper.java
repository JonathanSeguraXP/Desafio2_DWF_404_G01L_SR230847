package sv.edu.udb.UserSubscripcionAPI.mapper;

import sv.edu.udb.UserSubscripcionAPI.dto.SubscriptionRequestDto;
import sv.edu.udb.UserSubscripcionAPI.dto.SubscriptionResponseDto;
import sv.edu.udb.UserSubscripcionAPI.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    Subscription toEntity(SubscriptionRequestDto subscriptionRequestDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    SubscriptionResponseDto toDto(Subscription subscription);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntityFromDto(SubscriptionRequestDto subscriptionRequestDto, @MappingTarget Subscription subscription);
}