package com.jornadamilhas.api.repositories;

import com.jornadamilhas.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
