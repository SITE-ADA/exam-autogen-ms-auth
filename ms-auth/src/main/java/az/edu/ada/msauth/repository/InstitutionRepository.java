package az.edu.ada.msauth.repository;

import az.edu.ada.msauth.model.entities.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
