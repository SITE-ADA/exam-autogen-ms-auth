package az.edu.ada.msauth.repository;

import az.edu.ada.msauth.model.entities.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CustomUserDetailsRepository extends JpaRepository<CustomUserDetails, Long> {
    Optional<CustomUserDetails> findByUserId(Long userId);
}