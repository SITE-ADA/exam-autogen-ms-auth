package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.entities.UserType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserTypeService {
    UserType createUserType(UserType userType);
    Optional<UserType> getUserTypeById(Long id);
    UserType updateUserType(Long id, UserType userType);
    UserType patchUserType(Long id, Map<String, Object> updates);
    void deleteUserType(Long id);
    List<UserType> getAllUserTypes();
}