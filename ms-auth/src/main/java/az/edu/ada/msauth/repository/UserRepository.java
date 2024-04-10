package az.edu.ada.msauth.repository;

import az.edu.ada.msauth.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findInstructorsByInstitutionIdAndUserTypeId(Long institution_id, Long userTypeId);
}