package com.jornadamilhas.api.repositories;

import com.jornadamilhas.api.models.Destiny;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinyRepository extends JpaRepository<Destiny, Long> {
    List<Destiny> findByNameContainsIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
