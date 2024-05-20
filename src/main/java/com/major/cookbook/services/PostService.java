package com.major.cookbook.services;

import com.major.cookbook.model.Post;
import com.major.cookbook.model.User;

import java.util.List;

public interface PostService {
    // Create a new post
    public Post createPost(Post post);

    // GET all posts
    public List<Post> getPosts();

    // GET specific post by Id
    public Post getPostById(int postId);

    // Update a particular post
    public Post updatePost(Post post);

    // Delete a particular post
    public Post deletePost(int postId);

    // GETs all posts by a specific user
    public List<Post> findPostByUser(User user);

    // GET all posts containing the search keyword
    public List<Post> getSearchResults(String search);
}
