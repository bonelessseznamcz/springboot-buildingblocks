package com.flem.resthello.dto;

public class UserDtoV1 extends UserMmDto {
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;
}
