package com.eduforge.auth.repository;

import com.eduforge.auth.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** Finds a user by email address. */
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
