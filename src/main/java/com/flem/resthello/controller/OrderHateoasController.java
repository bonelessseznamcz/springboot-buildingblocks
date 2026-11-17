package com.flem.resthello.controller;

import com.flem.resthello.entity.Order;
import com.flem.resthello.exception.UserNotFoundException;
import com.flem.resthello.repository.OrderRepository;
import com.flem.resthello.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Validated
@RestController
@RequestMapping("/hateoas/users")
public class OrderHateoasController {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    OrderHateoasController(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{userId}/orders")
    public CollectionModel<EntityModel<Order>> getAllOrdersByUser(@PathVariable Long userId) throws UserNotFoundException {
        var userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User " + userId + " not found");
        }
        List<EntityModel<Order>> orderEntities = userOptional.get().getOrders().stream().map(o-> {
            List<Link> links = new ArrayList<>();
            try {
                links.add(linkTo(methodOn(OrderHateoasController.class).getOrderById(o.getId())).withSelfRel());
            } catch (UserNotFoundException uex) {
                //
            }
            links.add(linkTo(methodOn(UserHateoasController.class).getUser(o.getUser().getId())).withRel("user"));
            return EntityModel.of(o, links);
        }).toList();


        return CollectionModel.of(orderEntities);
    }

    @GetMapping("/orders/{id}")
    public EntityModel<Order> getOrderById(@PathVariable Long id) throws UserNotFoundException {
        var orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new UserNotFoundException("Order " + id + " not found");
        }
        var order = orderOptional.get();
        return EntityModel.of(order,
                linkTo(methodOn(OrderHateoasController.class).getOrderById(id)).withSelfRel(),
                linkTo(methodOn(UserHateoasController.class).getUser(order.getUser().getId())).withRel("user"));
    }
}
