package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.model.entities.UserType;
import az.edu.ada.msauth.repository.UserTypeRepository;
import az.edu.ada.msauth.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserTypeServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public UserType createUserType(UserType userType) {
        return userTypeRepository.save(userType);
    }

    @Override
    public List<UserType> getAllUserTypes() {
        return userTypeRepository.findAll();
    }

    @Override
    public Optional<UserType> getUserTypeById(Long id) {
        return userTypeRepository.findById(id);
    }

    @Override
    public UserType updateUserType(Long id, UserType updatedUserType) {
        Optional<UserType> existingUserTypeOptional = userTypeRepository.findById(id);

        if (existingUserTypeOptional.isPresent()) {
            updatedUserType.setId(id);
            return userTypeRepository.save(updatedUserType);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public UserType patchUserType(Long id, Map<String, Object> updates) {
        Optional<UserType> optionalUserType = userTypeRepository.findById(id);
        if (optionalUserType.isEmpty()) {
            return null;
        }

        UserType userType = optionalUserType.get();
        applyPatchToUserType(userType, updates);
        userTypeRepository.save(userType);
        return userType;
    }

    private void applyPatchToUserType(UserType userType, Map<String, Object> updates) {
        Class<?> clazz = userType.getClass();
        updates.forEach((key, value) -> {
            try {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(userType, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle the exception, possibly logging a warning or throwing a custom exception
            }
        });
    }

    public void deleteUserType(Long id) {
        userTypeRepository.deleteById(id);
    }
}
