package com.flem.resthello.controller;

import com.flem.resthello.dto.UserDtoV1;
import com.flem.resthello.dto.UserDtoV2;
import com.flem.resthello.exception.UserNotFoundException;
import com.flem.resthello.mapper.UserMmMapper;
import com.flem.resthello.service.UserService;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.flem.resthello.entity.User;

import java.util.function.Function;

@RestController
@RequestMapping("/versioning/header/modelmapper/user")
public class VersionedUserModelMapperController {

    private final UserService service;
    private final UserMmMapper mapper;

    VersionedUserModelMapperController(UserService service, UserMmMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public UserDtoV1 getUserByIdDefault(@PathVariable @Min(1) Long id) {
        return getUser(id, mapper::toDtoV1);
    }

    @GetMapping("/v1/{id}")
    public UserDtoV1 getUserByIdV1(@PathVariable @Min(1) Long id) {
        return getUser(id, mapper::toDtoV1);
    }

    @GetMapping("/v2/{id}")
    public UserDtoV2 getUserByIdV2(@PathVariable @Min(1) Long id) {
        return getUser(id, mapper::toDtoV2);
    }

    @GetMapping(value = "/{id}", params = "version=1")
    public UserDtoV1 getUserByParamIdV1(@PathVariable @Min(1) Long id) {
        return getUser(id, mapper::toDtoV1);
    }

    @GetMapping(value = "/{id}", params = "version=2")
    public UserDtoV2 getUserByParamIdV2(@PathVariable @Min(1) Long id) {
        return getUser(id, mapper::toDtoV2);
    }

    private <T> T getUser(Long id, Function<User, T> mapperFunction) {
        try {
            var user = service.getUserById(id);
            return mapperFunction.apply(user);
        } catch (UserNotFoundException uex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, uex.getLocalizedMessage());
        }
    }

}
