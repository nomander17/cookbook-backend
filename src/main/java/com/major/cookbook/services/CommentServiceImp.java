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
	public List<Comment> getCommentsForPost(int postId) {
		//Getting the post object to search using that
		Optional<Post> post = postRepo.findById(postId);
		return commentRepo.findByPost(post);
	}
	@Override
	public Comment getCommentById(int postId, int commentId) {
		Optional<Post> post = postRepo.findById(postId);
		Optional<Comment> comment = commentRepo.findByPostAndCommentId(post, commentId);
		return comment.orElse(null);
	}
	@Override
	public Comment updateComment(Comment updatedComment) {
		commentRepo.save(updatedComment);
		return updatedComment;
	}
	@Override
	public Comment deleteCommentByPostAndId(int postId, int commentId) {
		Optional<Post> post = postRepo.findById(postId);
		Optional<Comment> optionalComment = commentRepo.findByPostAndCommentId(post, commentId);
        if (optionalComment.isPresent()) {
            Comment commentToDelete = optionalComment.get();
            commentRepo.delete(commentToDelete);
            return commentToDelete;
        }
        return null; // Comment not found or not associated with the po
	}

	@Override
	public Comment deleteCommentById(int commentId) {
		Optional<Comment> optionalComment = commentRepo.findById(commentId);
		if (optionalComment.isPresent()) {
			Comment commentToDelete = optionalComment.get();
			commentRepo.delete(commentToDelete);
			return commentToDelete;
		}
		return null; // Comment not found
	}
	@Override
	public List<Comment> getAllComments() {
		return commentRepo.findAll();
	}
}
