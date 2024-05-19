package com.major.cookbook.dto;

import java.time.LocalDateTime;

public class AdminUpdatePostDTO {
    private int postId;
    private byte[] image;
    private String text;
    private LocalDateTime time;
    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
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
    
}
