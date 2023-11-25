package com.az.edu.ada.msauth.repository;

import az.edu.ada.sdpbackapi.models.Role;
import az.edu.ada.sdpbackapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByEmailIgnoreCase(String emailId);
}
