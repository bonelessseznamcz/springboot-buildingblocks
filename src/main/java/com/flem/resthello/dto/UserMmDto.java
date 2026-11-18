package com.flem.resthello.dto;

import com.flem.resthello.entity.Order;

import java.util.List;

public class UserMmDto {
    private Long userId;
    private String login;

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }

    private List<OrderDto> orders;

    public UserMmDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
