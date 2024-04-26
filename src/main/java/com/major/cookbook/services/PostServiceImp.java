package com.major.cookbook.services;

import com.major.cookbook.model.Post;
import com.major.cookbook.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceImp implements PostService {
    @Autowired
    private PostRepo postRepo;

    @Override
    public Post createPost(Post post) {
        postRepo.save(post);
        return post;
    }

    @Override
    public List<Post> getPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post getPostById(int postId) {
        return postRepo.findById(postId).orElse(null);
    }

    @Override
    public Post updatePost(Post updatedPost) {
        postRepo.save(updatedPost);
        return updatedPost;
    }

    @Override
    public Post deletePost(int postID) {
        Post post = getPostById(postID);
        postRepo.delete(post);
        return post;
    }
}
