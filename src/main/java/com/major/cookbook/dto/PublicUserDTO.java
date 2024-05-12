package com.major.cookbook.dto;

import java.util.Arrays;

public class PublicUserDTO {
    private Integer userId;
    private String username;
    private String name;
    private byte[] avatar;
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public byte[] getAvatar() {
        return avatar;
    }
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
    public PublicUserDTO() {
    }
    public PublicUserDTO(Integer userId, String username, String name, byte[] avatar) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.avatar = avatar;
    }
    @Override
    public String toString() {
        return "PublicUserDTO [userId=" + userId + ", username=" + username + ", name=" + name + ", avatar="
                + Arrays.toString(avatar) + "]";
    }
            
}
