package com.major.cookbook.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.major.cookbook.dto.LikeDTO;
import com.major.cookbook.dto.PublicUserDTO;
import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Like;
import com.major.cookbook.model.Post;
import com.major.cookbook.model.User;
import com.major.cookbook.repository.LikeRepo;
import com.major.cookbook.services.CommentService;
import com.major.cookbook.services.LikeService;
import com.major.cookbook.services.PostService;
import com.major.cookbook.services.UserService;
import com.major.cookbook.util.UserConversionUtil;



@RestController
@CrossOrigin
@RequestMapping("/api")
public class LikeController {
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LikeRepo likeRepo;
		
	//Add Like to a specific post ID by a specific user ID
	// LIKE a POST
	@PostMapping("/posts/{postId}/likes")
	public ResponseEntity<Object> createPostLike(@RequestBody LikeDTO likeDTO) {
        User user = userService.getUserById(likeDTO.getUserId());
        Post post = postService.getPostById(likeDTO.getPostId());
        if(user == null || post == null){
            return ResponseEntity.badRequest().body("User or Post does not exist.");
        }
        else {
        	if(likeRepo.alreadyLikedPost(likeDTO.getUserId(), likeDTO.getPostId())!=null) {
        		return ResponseEntity.status(HttpStatus.CONFLICT).body("User has already liked the post");
        	}else {
	        	Like like = new Like();
	        	like.setUser(user);
	        	like.setPost(post);
	        	like.setComment(null);
				like.setTime((LocalDateTime.now()));
				like.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(like.getUser()));
	        	Like addPostLike = this.likeService.addPostLike(like);
	            return ResponseEntity.ok(addPostLike);
        	}
        }
    }
	//Add Like to a specific commentID ID by a specific user ID
	// LIKE a COMMENT
	@PostMapping("/posts/{postId}/comments/{commentId}/likes")
	public ResponseEntity<Object> createCommentLike(@RequestBody LikeDTO likeDTO) {
        Comment comment = commentService.getCommentByCommentId(likeDTO.getCommentId());
		User user = userService.getUserById(likeDTO.getUserId());
        if(user == null || comment == null){
            return ResponseEntity.badRequest().body("Comment does not exist.");
        }
        else {
        	if(likeRepo.alreadyLikedComment(likeDTO.getUserId(), likeDTO.getCommentId())!=null) {
        		return ResponseEntity.badRequest().body("User has already liked the comment");
        	}else {
	        	Like like = new Like();
	        	like.setUser(user);
	        	like.setPost(null);
	        	like.setComment(comment);
				like.setTime(LocalDateTime.now());
				like.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(like.getUser()));
	        	Like addPostLike = this.likeService.addPostLike(like);
	            return ResponseEntity.ok(addPostLike);
        	}
        }
    }
	
	//GET all likes under a specific post ID
	@GetMapping("/posts/{postId}/likes")
	public ResponseEntity<Object> getPostLikes(@PathVariable String postId) {
        List<Like> likes = this.likeService.getLikesByPostId(Integer.parseInt(postId));
        if (likes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
			for (Like like: likes) {
				PublicUserDTO publicUserDTO = UserConversionUtil.convertToPublicUserDTO(like.getUser());
                like.setPublicUserDTO(publicUserDTO);
			}
            return ResponseEntity.ok(likes);
        }
    }
	
	//GET all likes under a specific comment ID
	@GetMapping("/posts/{postId}/comments/{commentId}/likes")
	public ResponseEntity<Object> getCommentLikes(@PathVariable String commentId) {
        List<Like> likes = this.likeService.getLikesByCommentId(Integer.parseInt(commentId));
        if (likes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
			for (Like like: likes) {
				like.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(like.getUser()));
			}
            return ResponseEntity.ok(likes);
        }
    }
	
	//Delete a like for a post by like ID
	@DeleteMapping("/posts/{postId}/likes/{likeId}")
	public ResponseEntity<Object> deletePostLike(@PathVariable String likeId) {
		Like like = this.likeService.deleteLikeById(Integer.parseInt(likeId));
		like.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(like.getUser()));
    	return ResponseEntity.ok(like);
    }
	
	//Delete a like for a comment by like ID
	@DeleteMapping("/posts/{postId}/comments/{commentId}/likes/{likeId}")
	public ResponseEntity<Object> deleteCommentLike(@PathVariable String likeId) {
		Like like = this.likeService.deleteLikeById(Integer.parseInt(likeId));
		like.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(like.getUser()));
    	return ResponseEntity.ok(this.likeService.deleteLikeById(Integer.parseInt(likeId)));
    }
}
