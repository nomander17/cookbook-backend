package com.major.cookbook.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Like;
import com.major.cookbook.model.Post;
import com.major.cookbook.repository.CommentRepo;
import com.major.cookbook.repository.LikeRepo;
import com.major.cookbook.repository.PostRepo;

@Service
public class LikeServiceImp implements LikeService {

	@Autowired
	private LikeRepo likeRepo;

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Override
	public Like addPostLike(Like like) {
		likeRepo.save(like);
		return like;
	}

	@Override
	public Like addCommentLike(Like like) {
		likeRepo.save(like);
		return like;
	}

	@Override
	public List<Like> getAllLikes() {
		return likeRepo.findAll();
		//throw new UnsupportedOperationException("Unimplemented method 'getAllLikes'");
	}

	@Override
	public Like deleteLikeById(int likeId) {
		Optional<Like> optionalLike = likeRepo.findById(likeId);
		if (optionalLike.isPresent()) {
			Like likeToDelete = optionalLike.get();
			likeRepo.delete(likeToDelete);
			return likeToDelete;
		}
		return null;
	}

	@Override
	public Like updateLike(Like updatedLike) {
		likeRepo.save(updatedLike);
		return updatedLike;
	}

	@Override
	public List<Like> getLikesByPostId(int postId) {
		Optional<Post> post = postRepo.findById(postId);
		return likeRepo.findByPost(post);
	}

	@Override
	public List<Like> getLikesByCommentId(int commentId) {
		Optional<Comment> comment = commentRepo.findById(commentId);
		if (comment.isPresent()) {
			return likeRepo.findByComment(comment);
		} else {
			return new ArrayList<>();
		}
		// return likeRepo.findByComment(comment);
	}
	 
}
