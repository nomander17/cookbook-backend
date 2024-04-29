package com.major.cookbook.controller;

import com.major.cookbook.dto.PostDTO;
import com.major.cookbook.model.Post;
import com.major.cookbook.model.User;
import com.major.cookbook.services.PostService;
import com.major.cookbook.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    // Returns a specified post
    @GetMapping("/{postId}")
    public ResponseEntity<Object> getPost(@PathVariable String postId) {
        Post post = this.postService.getPostById(Integer.parseInt(postId));
        if (post == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(post);
        }
    }

    // Returns ALL posts
    @GetMapping("")
    public ResponseEntity<Object> getPosts() {
        List<Post> posts = this.postService.getPosts();
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(posts);
        }
    }

    // Create a new post
    @PostMapping("")
    public ResponseEntity<Object> createPost(@RequestBody PostDTO postDTO) {
        User user = userService.getUserById(postDTO.getUserId());
        if(user == null){
            return ResponseEntity.badRequest().body("User does not exist.");
        }
        else {
            Post post = new Post();
            post.setUser(user);
            post.setText(postDTO.getText());
            post.setImage(postDTO.getImage());
            post.setTime(postDTO.getTime());
            Post createdPost = this.postService.createPost(post);
            return ResponseEntity.ok(createdPost);
        }
    }

    // Update a specified post
    @PutMapping("/{postId}")
    public ResponseEntity<Object> updatePost(@PathVariable String postId, @RequestBody Post updatedPost) {
        Post post = this.postService.getPostById(Integer.parseInt(postId));
        logger.debug("PostId : {}", postId);
        logger.debug("Post: {}", post);
        if (post == null) {
            logger.warn("PostId {} not found", postId);
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(this.postService.updatePost(updatedPost));
        }
    }

    // Delete a specified post
    @DeleteMapping("/{postId}")
    public ResponseEntity<Post> deletePost(@PathVariable String postId) {
        return ResponseEntity.ok(this.postService.deletePost(Integer.parseInt(postId)));
    }
}
