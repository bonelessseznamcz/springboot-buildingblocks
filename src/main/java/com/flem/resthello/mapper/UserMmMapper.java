package com.flem.resthello.mapper;

import com.flem.resthello.dto.UserMmDto;
import com.flem.resthello.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMmMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "login", source = "userName")
    @Mapping(target = "orders", source = "orders")
    UserMmDto toDto(User user);
}
