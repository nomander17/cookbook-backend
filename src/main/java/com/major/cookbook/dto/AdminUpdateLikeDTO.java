package com.major.cookbook.dto;

import java.time.LocalDateTime;

public class AdminUpdateLikeDTO {
    private LocalDateTime time;
    private int commentId;
    private int postId;
    private int likeId;
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public int getCommentId() {
        return commentId;
    }
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public int getLikeId() {
        return likeId;
    }
    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }
    
}
