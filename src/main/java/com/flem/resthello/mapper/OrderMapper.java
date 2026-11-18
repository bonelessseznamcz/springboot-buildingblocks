package com.flem.resthello.mapper;

import com.flem.resthello.dto.OrderDto;
import com.flem.resthello.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "description", target = "description")
    OrderDto toOrderDto(Order order);
}
