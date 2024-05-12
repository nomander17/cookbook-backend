package com.major.cookbook.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.major.cookbook.dto.PublicUserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Integer likeId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    @JsonProperty("user") // json return name
    private PublicUserDTO publicUserDTO;

    @Column(name = "time")
    private LocalDateTime time;

    public Integer getLikeId() {
        return likeId;
    }

    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PublicUserDTO getPublicUserDTO() {
        return publicUserDTO;
    }

    public void setPublicUserDTO(PublicUserDTO publicUserDTO) {
        this.publicUserDTO = publicUserDTO;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    // This method will be called by Jackson to serialize postId
    @JsonProperty("postId")
    public Integer getPostId() {
        return post != null ? post.getPostId() : null;
    }

    @JsonProperty("commentId")
    public Integer getCommentId() {
        return comment != null ? comment.getCommentId() : null;
    }

    public Like() {
    }

    public Like(Integer likeId, Post post, Comment comment, User user, PublicUserDTO publicUserDTO,
            LocalDateTime time) {
        this.likeId = likeId;
        this.post = post;
        this.comment = comment;
        this.user = user;
        this.publicUserDTO = publicUserDTO;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Like [likeId=" + likeId + ", post=" + post + ", comment=" + comment + ", user=" + user
                + ", publicUserDTO=" + publicUserDTO + ", time=" + time + "]";
    }

    
}