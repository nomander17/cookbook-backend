package com.major.cookbook.services;

import com.major.cookbook.model.Post;
import java.util.List;

public interface PostService {
    // Create a new post
    public Post createPost(Post post);

    // GET all posts
    public List<Post> getPosts();

    // GET specific post by ID
    public Post getPostById(int postId);

    // Update a particular post
    public Post updatePost(Post post);

    // Delete a particular post
    public Post deletePost(int postID);
}
