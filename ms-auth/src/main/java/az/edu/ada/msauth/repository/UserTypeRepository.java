package az.edu.ada.msauth.repository;

import az.edu.ada.msauth.model.entities.UserType;
import az.edu.ada.msauth.model.enums.EUserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByName(EUserType name);
}