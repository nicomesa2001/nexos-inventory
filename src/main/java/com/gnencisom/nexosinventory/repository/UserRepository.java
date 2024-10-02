package com.gnencisom.nexosinventory.repository;

import com.gnencisom.nexosinventory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuario por nombre
    Optional<User> findByName(String name);
}
