package com.jornadamilhas.api.repositories;

import com.jornadamilhas.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    UserDetails findByEmail(String username);
}
