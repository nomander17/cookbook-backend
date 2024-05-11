package com.major.cookbook.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.major.cookbook.dto.PublicUserDTO;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    @JsonProperty("user") // json return name
    private PublicUserDTO publicUserDTO;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "text", length = 1000, nullable = false)
    private String text;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    @JsonManagedReference
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public PublicUserDTO getPublicUserDTO() {
        return publicUserDTO;
    }

    public void setPublicUserDTO(PublicUserDTO publicUserDTO) {
        this.publicUserDTO = publicUserDTO;
    }

    public Comment() {
    }

    public Comment(Integer commentId, Post post, User user, PublicUserDTO publicUserDTO, LocalDateTime time,
            String text, byte[] image, List<Like> likes) {
        this.commentId = commentId;
        this.post = post;
        this.user = user;
        this.publicUserDTO = publicUserDTO;
        this.time = time;
        this.text = text;
        this.image = image;
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Comment [commentId=" + commentId + ", post=" + post + ", user=" + user + ", publicUserDTO="
                + publicUserDTO + ", time=" + time + ", text=" + text + ", image=" + Arrays.toString(image) + ", likes="
                + likes + "]";
    }
    
}
