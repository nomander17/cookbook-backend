package com.major.cookbook.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.major.cookbook.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
