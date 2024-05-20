package com.major.cookbook.controller;

import com.major.cookbook.dto.PostDTO;
import com.major.cookbook.dto.PublicUserDTO;
import com.major.cookbook.dto.SearchDTO;
import com.major.cookbook.model.Like;
import com.major.cookbook.model.Post;
import com.major.cookbook.model.User;
import com.major.cookbook.services.PostService;
import com.major.cookbook.services.UserService;
import com.major.cookbook.util.UserConversionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
            // Convert User to UserDTO
            PublicUserDTO publicUserDTO = UserConversionUtil.convertToPublicUserDTO(post.getUser());
            // Set the DTO in the post before returning
            post.setPublicUserDTO(publicUserDTO);
            List<Like> likes = post.getLikes();
            // List<Like> updatedLikes = new ArrayList<>();
            for (Like like : likes) {
                like.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(like.getUser()));
            }
            post.setLikes(likes);
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
            // Convert User to PublicUserDTO for each post
            for (Post post : posts) {
                PublicUserDTO publicUserDTO = UserConversionUtil.convertToPublicUserDTO(post.getUser());
                post.setPublicUserDTO(publicUserDTO);
            }
            return ResponseEntity.ok(posts);
        }
    }

    //GETs all the posts searched by the user
    @PostMapping("/search")
    public ResponseEntity<Object> searchPost(@RequestBody SearchDTO searchDTO) {
        String search = searchDTO.getQuery();
        search = "%"+search+"%";
        List<Post> posts = postService.getSearchResults(search);
        for (Post post: posts) {
            post.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(post.getUser()));
        }
        return ResponseEntity.ok(posts);
    }   

    // Create a new post
    @PostMapping("")
    public ResponseEntity<Object> createPost(@RequestBody PostDTO postDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        // User user = userService.getUserById(postDTO.getUserId());
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body("User does not exist.");
        } else if (!user.getUserId().equals(postDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User ID in the request does not match the authenticated user.");
        } else {
            Post post = new Post();
            post.setUser(user);
            post.setText(postDTO.getText());
            post.setImage(postDTO.getImage());
            post.setTime(LocalDateTime.now());
            Post createdPost = this.postService.createPost(post);
            return ResponseEntity.ok(createdPost);
        }
    }

    // Update a specified post
    @PutMapping("/{postId}")
    public ResponseEntity<Object> updatePost(@PathVariable String postId, @RequestBody Post updatedPost, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.getUserByUsername(username);
        if(user == null){
            return ResponseEntity.badRequest().body("User does not exist");
        } else if (!user.getUserId().equals(updatedPost.getUser().getUserId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("User ID doesn't correspond to authenticated user");
        }else{
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
    }

    // Delete a specified post
    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable String postId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.getUserByUsername(username);
        Post post = postService.getPostById(Integer.parseInt(postId));
        if(user == null){
            return ResponseEntity.badRequest().body("User does not exist");
        } else if (!user.getUserId().equals(post.getUser().getUserId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("User ID doesn't correspond to authenticated user");
        }else {
            return ResponseEntity.ok(this.postService.deletePost(Integer.parseInt(postId)));
        }
    }
}
