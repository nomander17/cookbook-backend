package com.major.cookbook.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.major.cookbook.dto.CommentDTO;
import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Like;
import com.major.cookbook.model.Post;
import com.major.cookbook.model.User;
import com.major.cookbook.services.CommentService;
import com.major.cookbook.services.PostService;
import com.major.cookbook.services.UserService;
import com.major.cookbook.util.UserConversionUtil;

@RestController
@CrossOrigin
@RequestMapping("/api/posts")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    // Returns a specified comment from a specific post
    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Object> getComment(@PathVariable String postId, @PathVariable String commentId) {
        Comment comment = this.commentService.getCommentByCommentId(Integer.parseInt(commentId));
        if (comment == null) {
            return ResponseEntity.notFound().build();
        } else {
            comment.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(comment.getUser()));
            comment.getPost().setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(comment.getPost().getUser()));
            // for likes to the comment itself
            for (Like like: comment.getLikes()) {
                like.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(like.getUser()));
            }
            return ResponseEntity.ok(comment);
        }
    }

    // Returns ALL comments under a specific postId
    @GetMapping("/{postId}/comments")
    public ResponseEntity<Object> getComments(@PathVariable String postId) {
        List<Comment> comments = this.commentService.getCommentsForPost(Integer.parseInt(postId));
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (Comment comment : comments) {
                comment.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(comment.getUser()));
                comment.getPost().setImage(null);
                comment.getPost().setComments(null);
                comment.getPost().setLikes(null);
                comment.setLikes(null);
                comment.setImage(null);
                comment.setText(null);
                comment.setTime(null);
            }
            return ResponseEntity.ok(comments);
        }
    }

    // Create a new comment under a specific postId
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Object> createComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User authenticatedUser = userService.getUserByUsername(username);
        if (authenticatedUser == null){
            return ResponseEntity.badRequest().body("User does not exist.");
        } else if(!authenticatedUser.getUserId().equals(commentDTO.getUserId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User ID in the request does not match the authenticated user.");
        }else{
            User user = userService.getUserById(commentDTO.getUserId());
            Post post = postService.getPostById(commentDTO.getPostId());
            if (user == null || post == null) {
                return ResponseEntity.badRequest().body("User or Post does not exist.");
            } else {
                Comment comment = new Comment();
                comment.setUser(user);
                comment.setPost(post);
                comment.setText(commentDTO.getText());
                comment.setImage(commentDTO.getImage());
                comment.setTime(LocalDateTime.now());
                Comment createdComment = this.commentService.createComment(comment);
                return ResponseEntity.ok(createdComment);
            }
        }
    }

    // Update a specified comment under a post
    @PutMapping("{postId}/comments/{commentId}")
    public ResponseEntity<Object> updateComment(@PathVariable String postId, @PathVariable String commentId,
            @RequestBody Comment updatedComment, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User authenticatedUser = userService.getUserByUsername(username);    
        if (authenticatedUser == null) {
            return ResponseEntity.badRequest().body("User does not exist.");
        } else if(!authenticatedUser.getUserId().equals(updatedComment.getUser().getUserId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("User ID doesn't correspond to authenticated user");
        }else{
            Comment comment = this.commentService.getCommentById(Integer.parseInt(postId), Integer.parseInt(commentId));
            if (comment == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(this.commentService.updateComment(updatedComment));
            }
        }
    }

    // Delete a specified comment
    @DeleteMapping("{postId}/comments/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable String postId, @PathVariable String commentId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.getUserByUsername(username);
        Comment comment = commentService.getCommentByCommentId(Integer.parseInt(commentId));
        if(user == null){
            return ResponseEntity.badRequest().body("User does not exist");
        } else if (!user.getUserId().equals(comment.getUser().getUserId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("User ID doesn't correspond to authenticated user");
        } else {
        return ResponseEntity.ok(
                this.commentService.deleteCommentByPostAndId(Integer.parseInt(postId), Integer.parseInt(commentId)));
        }
    }
}