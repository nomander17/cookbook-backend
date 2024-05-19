package com.major.cookbook.controller;

import com.major.cookbook.dto.UserDTOForAdmin;
import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Like;
import com.major.cookbook.model.Post;
import com.major.cookbook.model.User;
import com.major.cookbook.services.CommentService;
import com.major.cookbook.services.LikeService;
import com.major.cookbook.services.PostService;
import com.major.cookbook.services.UserService;
import com.major.cookbook.util.UserConversionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/api")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<UserDTOForAdmin> userDTOs = new ArrayList<>();
            for (User user: users) {
                userDTOs.add(UserConversionUtil.convertToUserDTOForAdmin(user));
            }
            return ResponseEntity.ok(userDTOs);
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<Object> getPosts() {
        List<Post> posts = postService.getPosts();
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for(Post post : posts) {
                post.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(post.getUser()));
            }
            return ResponseEntity.ok(posts);
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<Object> getComments() {
        List<Comment> comments = commentService.getAllComments();
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for(Comment comment: comments) {
                comment.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(comment.getUser()));
            }
            return ResponseEntity.ok(comments);
        }
    }

    @GetMapping("/likes")
    public ResponseEntity<Object> getLikes() {
        List<Like> likes = likeService.getAllLikes();
        if (likes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for(Like like: likes) {
                like.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(like.getUser()));
            }
            return ResponseEntity.ok(likes);
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable String userId) {
        userService.deleteUser(Integer.parseInt(userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable String postId) {
        postService.deletePost(Integer.parseInt(postId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable String commentId) {
        commentService.deleteCommentById(Integer.parseInt(commentId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/likes/{likeId}")
    public ResponseEntity<Object> deleteLike(@PathVariable String likeId) {
        likeService.deleteLikeById(Integer.parseInt(likeId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable String userId, @RequestBody User user) {
        User oldUser = userService.getUserById(Integer.parseInt(userId));
        // if password was not changed, get the old one
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        } else {
            // password was changed
            // encypt and save
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<Object> updatePost(@PathVariable String postId, @RequestBody Post post) {
        postService.updatePost(post);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Object> updateComment(@PathVariable String commentId, @RequestBody Comment comment) {
        commentService.updateComment(comment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/likes/{likeId}")
    public ResponseEntity<Object> updateLike(@PathVariable String likeId, @RequestBody Like like) {
        likeService.updateLike(like);
        return ResponseEntity.ok().build();
    }
}
