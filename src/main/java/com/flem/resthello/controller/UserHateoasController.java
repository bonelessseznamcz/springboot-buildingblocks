package com.flem.resthello.controller;

import com.flem.resthello.entity.User;
import com.flem.resthello.exception.UserNotFoundException;
import com.flem.resthello.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Validated
@RestController
@RequestMapping("/hateoas/users")
public class UserHateoasController {

    private final UserService service;

    UserHateoasController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUser(@PathVariable Long id) {
        User user;
        try {
            user = service.getUserById(id);
        } catch (UserNotFoundException uex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, uex.getLocalizedMessage());
        }

        EntityModel<User> model = EntityModel.of(
                user,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(UserHateoasController.class).getUser(id)
                ).withSelfRel(),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(UserHateoasController.class).getAllUsers()
                ).withRel("all-users")
        );

        return model;
    }

    @GetMapping
    public CollectionModel<EntityModel<User>> getAllUsers() {
        List<EntityModel<User>> users = service.getAllUsers().stream()
                .map(u -> EntityModel.of(u,
                        WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(UserHateoasController.class).getUser(u.getId())
                        ).withSelfRel()))
                .toList();

        return CollectionModel.of(users);
    }

}
