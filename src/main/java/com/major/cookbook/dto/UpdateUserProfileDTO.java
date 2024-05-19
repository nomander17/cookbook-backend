package com.major.cookbook.dto;

public class UpdateUserProfileDTO {
    private int userId;
    private String username;
    private String name;
    private String email;
    private byte[] avatar;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public byte[] getAvatar() {
        return avatar;
    }
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
    public UpdateUserProfileDTO() {
    }
    public UpdateUserProfileDTO(int userId, String username, String name, String email, byte[] avatar) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }
}
