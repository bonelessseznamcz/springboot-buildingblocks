package com.flem.resthello.controller.filtered;

import com.flem.resthello.entity.User;
import com.flem.resthello.exception.UserNotFoundException;
import com.flem.resthello.service.UserService;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Validated
@RestController
@RequestMapping("/filtered/users")
public class UserMappingJacksonController {

    private final UserService service;

    UserMappingJacksonController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable @Min(1) Long id) {
        try {
            var user = service.getUserById(id);
            return user;
        } catch (UserNotFoundException uex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, uex.getLocalizedMessage());
        }
    }
}
