package com.major.cookbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.Post;
import com.major.cookbook.model.User;

public interface PostRepo extends JpaRepository <Post, Integer> {

    List<Post> findByUser(User user);

    // Finds posts where text=%:search%
    List<Post> findByTextLike(String search);

}
