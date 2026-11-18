package com.flem.resthello.controller;

import com.flem.resthello.dto.UserMmDto;
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

@RestController
@RequestMapping("/modelmapper/user")
public class UserModelMapperController {

    private final UserService service;
    private final UserMmMapper mapper;

    UserModelMapperController(UserService service, UserMmMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public UserMmDto getUserById(@PathVariable @Min(1) Long id) {
        try {
            var user = service.getUserById(id);
            return mapper.toDto(user);
        } catch (UserNotFoundException uex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, uex.getLocalizedMessage());
        }
    }

}
