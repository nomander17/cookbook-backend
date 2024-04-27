package com.major.cookbook.dto;

import java.time.LocalDateTime;

public class PostDTO {
    private int userID;
    private String text;
    private LocalDateTime time;
    private byte[] image;
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
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
    public PostDTO(int userID, String text, LocalDateTime time, byte[] image) {
        this.userID = userID;
        this.text = text;
        this.time = time;
        this.image = image;
    }
}
