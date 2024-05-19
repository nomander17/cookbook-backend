package com.major.cookbook.dto;

import java.time.LocalDateTime;

public class AdminUpdateCommentDTO {
    private int commentId;
    private byte[] image;
    private String text;
    private LocalDateTime time;
    public int getCommentId() {
        return commentId;
    }
    public void setCommentId(int postId) {
        this.commentId = postId;
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
