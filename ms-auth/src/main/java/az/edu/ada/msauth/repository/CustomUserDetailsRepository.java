package az.edu.ada.msauth.repository;

import az.edu.ada.msauth.model.entities.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserDetailsRepository extends JpaRepository<CustomUserDetails, Long> {
}