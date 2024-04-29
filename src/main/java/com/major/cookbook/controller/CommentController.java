package com.major.cookbook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.major.cookbook.model.Post;
import com.major.cookbook.model.User;
import com.major.cookbook.services.CommentService;
import com.major.cookbook.services.PostService;
import com.major.cookbook.services.UserService;

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
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	
	// Returns a specified comment from a specific post
    @GetMapping("/{postId}/comments/{commentID}")
    public ResponseEntity<Object> getComment(@PathVariable String postID, @PathVariable String commentID) {
        Comment comment = this.commentService.getCommentById(Integer.parseInt(postID), Integer.parseInt(commentID));
        if (comment == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(comment); 
        }
    }

    // Returns ALL comments under a specific postID
    @GetMapping("/{postID}/comments")
    public ResponseEntity<Object> getComments(@PathVariable String postID) {
        List<Comment> comments = this.commentService.getCommentsForPost(Integer.parseInt(postID));
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(comments);
        }
    }

    // Create a new comment under a specific postID
    @PostMapping("/{postID}/comments")
    public ResponseEntity<Object> createComment(@RequestBody CommentDTO commentDTO) {
        User user = userService.getUserById(commentDTO.getUserId());
        Post post = postService.getPostById(commentDTO.getPostId());
        if(user == null || post == null){
            return ResponseEntity.badRequest().body("User or Post does not exist.");
        }
        else {
            Comment comment = new Comment();
            comment.setUser(user);
            comment.setPost(post);
            comment.setText(commentDTO.getText());
            comment.setImage(commentDTO.getImage());
            comment.setTime(commentDTO.getTime());
            Comment createdComment = this.commentService.createComment(comment);
            return ResponseEntity.ok(createdComment);
        }
    }

    // Update a specified comment under a post
    @PutMapping("{postID}/comments/{commentID}")
    public ResponseEntity<Object> updateComment(@PathVariable String postID, @PathVariable String commentID, @RequestBody Comment updatedComment) {
    	Comment comment = this.commentService.getCommentById(Integer.parseInt(postID),Integer.parseInt(commentID));
        logger.debug("CommentID : {}", commentID);
        logger.debug("Comment: {}", comment);
        if(comment == null) {
            logger.warn("CommentID {} not found", commentID);
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(this.commentService.updateComment(updatedComment));
        }
    }

    // Delete a specified comment
    @DeleteMapping("{postID}/comments/{commentID}")
    public ResponseEntity<Comment> deleteComment(@PathVariable String postID, @PathVariable String commentID) {
    	return ResponseEntity.ok(this.commentService.deleteCommentByPostAndId(Integer.parseInt(postID), Integer.parseInt(commentID)));
    }
}