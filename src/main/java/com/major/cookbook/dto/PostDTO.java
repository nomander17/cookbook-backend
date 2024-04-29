package com.major.cookbook.dto;

import java.time.LocalDateTime;

public class PostDTO {
    private int userId;
    private String text;
    private LocalDateTime time;
    private byte[] image;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public PostDTO() {
    }
    public PostDTO(int userId, String text, LocalDateTime time, byte[] image) {
        this.userId = userId;
        this.text = text;
        this.time = time;
        this.image = image;
    }
}
