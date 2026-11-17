package com.flem.resthello.controller;

import com.flem.resthello.entity.Order;
import com.flem.resthello.entity.User;
import com.flem.resthello.exception.UserNotFoundException;
import com.flem.resthello.repository.OrderRepository;
import com.flem.resthello.repository.UserRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
public class OrderController {

    private UserRepository userRepository;
    private OrderRepository orderRepository;

    OrderController(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{userId}/orders")
    public List<Order> getAllOrdersByUser(@PathVariable Long userId) throws UserNotFoundException {
        var userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User " + userId + " not found");
        }
        return userOptional.get().getOrders();
    }

    @PostMapping("/{userId}/orders/")
    public void createOrder(@PathVariable Long userId, @RequestBody Order order) throws UserNotFoundException {
        var userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        order.setUser(userOptional.get());
        orderRepository.save(order);
    }

    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable Long id) throws UserNotFoundException {
        var orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new UserNotFoundException("Order " + id + " not found");
        }
        return orderOptional.get();
    }
}
