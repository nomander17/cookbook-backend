package com.major.cookbook.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.major.cookbook.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    public Optional<User> findByEmail(String email);
    
    //Used to find the User with isAdmin=true
	public User findUserIdByIsAdminTrue();

    public Optional<User> findByUsername(String username);
 
}
