package com.major.cookbook.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Post;
import com.major.cookbook.repository.CommentRepo;
import com.major.cookbook.repository.PostRepo;

@Service
public class CommentServiceImp implements CommentService{	
	
	@Autowired
	private CommentRepo commentRepo;
    @Autowired
	private PostRepo postRepo;
	
    @Override
	public Comment createComment(Comment comment) {
		commentRepo.save(comment);
		return comment;
	}
	@Override
	public List<Comment> getCommentsForPost(int postID) {
		Optional<Post> post = postRepo.findById(postID);
		return commentRepo.findByPost(post);
	}
	@Override
	public Comment getCommentById(int postID, int commentID) {
		Optional<Post> post = postRepo.findById(postID);
		Optional<Comment> comment = commentRepo.findByPostAndCommentId(post, commentID);
		return comment.orElse(null);
	}
	@Override
	public Comment updateComment(Comment updatedComment) {
		commentRepo.save(updatedComment);
		return updatedComment;
	}
	@Override
	public Comment deleteCommentByPostAndId(int postID, int commentID) {
		Optional<Post> post = postRepo.findById(postID);
		Optional<Comment> optionalComment = commentRepo.findByPostAndCommentId(post, commentID);
        if (optionalComment.isPresent()) {
            Comment commentToDelete = optionalComment.get();
            commentRepo.delete(commentToDelete);
            return commentToDelete;
        }
        return null; // Comment not found or not associated with the po
	}
    
	
}
