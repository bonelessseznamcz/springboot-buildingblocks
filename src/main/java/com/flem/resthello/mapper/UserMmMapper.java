package com.flem.resthello.mapper;

import com.flem.resthello.dto.UserDtoV1;
import com.flem.resthello.dto.UserDtoV2;
import com.flem.resthello.dto.UserMmDto;
import com.flem.resthello.entity.User;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMmMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "login", source = "userName")
    @Mapping(target = "orders", source = "orders")
    UserMmDto toDto(User user);

    @InheritConfiguration(name = "toDto")
    @Mapping(target = "address", source = "address")
    UserDtoV1 toDtoV1(User user);

    @InheritConfiguration(name = "toDto")
    UserDtoV2 toDtoV2(User user);
}
