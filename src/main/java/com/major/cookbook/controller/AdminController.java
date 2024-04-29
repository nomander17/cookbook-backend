package com.major.cookbook.controller;

import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Like;
import com.major.cookbook.model.Post;
import com.major.cookbook.model.User;
import com.major.cookbook.services.CommentService;
import com.major.cookbook.services.LikeService;
import com.major.cookbook.services.PostService;
import com.major.cookbook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/users/")
    public ResponseEntity<Object> getUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("/posts/")
    public ResponseEntity<Object> getPosts() {
        List<Post> posts = postService.getPosts();
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(posts);
        }
    }

    @GetMapping("/comments/")
    public ResponseEntity<Object> getComments() {
        List<Comment> comments = commentService.getAllComments();
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(comments);
        }
    }

    @GetMapping("/likes/")
    public ResponseEntity<Object> getLikes() {
        List<Like> likes = likeService.getAllLikes();
        if (likes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(likes);
        }
    }

    @DeleteMapping("/users/{userId}/")
    public ResponseEntity<Object> deleteUser(@PathVariable String userId) {
        userService.deleteUser(Integer.parseInt(userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{postId}/")
    public ResponseEntity<Object> deletePost(@PathVariable String postId) {
        postService.deletePost(Integer.parseInt(postId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}/")
    public ResponseEntity<Object> deleteComment(@PathVariable String commentId) {
        commentService.deleteCommentById(Integer.parseInt(commentId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/likes/{likeId}/")
    public ResponseEntity<Object> deleteLike(@PathVariable String likeId) {
        likeService.deleteLikeById(Integer.parseInt(likeId));
        return ResponseEntity.ok().build();
    }
}