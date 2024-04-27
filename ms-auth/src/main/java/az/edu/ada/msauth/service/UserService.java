package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.dto.InstitutionRepresentativeDetailsDTO;
import az.edu.ada.msauth.model.dto.InstructorDetailsDTO;
import az.edu.ada.msauth.model.dto.UserDetailsDTO;
import az.edu.ada.msauth.model.entities.User;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<InstitutionRepresentativeDetailsDTO> getInstitutionRepresentativesByInstitutionId(Long institutionId, Long userTypeId);

    List<InstructorDetailsDTO> getInstructorsByInstitutionId(Long institutionId, Long userTypeId);

    List<UserDetailsDTO> getAllInstitutionReps(Long userTypeId);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    void deleteUser(Long id);

    User patchUser(Long id, Map<String, Object> updates);
}
