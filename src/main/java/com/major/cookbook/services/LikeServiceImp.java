package com.major.cookbook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.major.cookbook.model.Like;
import com.major.cookbook.repository.LikeRepo;

@Service
public class LikeServiceImp implements LikeService {

	@Autowired
	private LikeRepo likeRepo;

	@Override
	public Like addPostLike(Like like) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Like addCommentLike(Like like) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Like alreadyLikedPost(int userId, int postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Like alreadyLikedComment(int userId, int commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Like> getPostLike(int postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Like> getCommentLike(int commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Like> getAllLikes() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getAllLikes'");
	}

	@Override
	public void deleteLikeById(int int1) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteLikeById'");
	}

	@Override
	public void updateLike(Like like) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateLike'");
	}
	 
}
