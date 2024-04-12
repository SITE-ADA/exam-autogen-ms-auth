package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.dto.InstructorDetailsDTO;
import az.edu.ada.msauth.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<InstructorDetailsDTO> getInstructorsByInstitutionId(Long institutionId, Long userTypeId);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    void deleteUser(Long id);

}
