package com.flem.resthello.service;

import com.flem.resthello.entity.User;
import com.flem.resthello.exception.UserExistsException;
import com.flem.resthello.exception.UserNotFoundException;
import com.flem.resthello.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User createUser(User newUser) throws UserExistsException {
        if (repository.getUSerByUserName(newUser.getUserName()) != null) {
            throw new UserExistsException("User " + newUser.getUserName() + " already exists!");
        }
        return repository.save(newUser);
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("User " + id + " not found!") );
    }

    public User updateUser(User user) throws UserNotFoundException {
        getUserById(user.getId());
        return repository.save(user);
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        repository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found, can't be deleted!"));
        repository.deleteById(id);
    }

    public User getUserByUserName(String userName) {
        return repository.getUSerByUserName(userName);
    }
}
