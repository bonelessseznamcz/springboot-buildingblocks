package com.flem.resthello.controller;

import com.flem.resthello.entity.User;
import com.flem.resthello.exception.UserExistsException;
import com.flem.resthello.exception.UserNotFoundException;
import com.flem.resthello.exception.UsernameNotFoundException;
import com.flem.resthello.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
        try {
            var createdUser = service.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(createdUser.getId()).toUri());
            return new ResponseEntity<>(createdUser, headers, HttpStatus.CREATED);
        } catch (UserExistsException uex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, uex.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable @Min(1) Long id) {
        try {
            return service.getUserById(id);
        } catch (UserNotFoundException uex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, uex.getLocalizedMessage());
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        if (user.getId() == null || !user.getId().equals(id)) {
            user.setId(id);
        }
        try {
            return service.updateUser(user);
        } catch (UserNotFoundException uex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, uex.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        try {
            service.deleteUser(id);
        } catch (UserNotFoundException uex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, uex.getLocalizedMessage());
        }
    }

    @GetMapping("/username/{userName}")
    public User getUserById(@PathVariable String userName) throws UsernameNotFoundException {
        var user = service.getUserByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + userName + " not found");
        }
        return user;
    }
}
