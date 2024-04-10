package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.dto.InstructorDetailsDTO;
import az.edu.ada.msauth.model.entities.User;

import java.util.List;

public interface UserService {
    List<InstructorDetailsDTO> getInstructorsByInstitutionId(Long institutionId, Long userTypeId);

    List<User> getAllUsers();
    void deleteUser(Long id);

}
