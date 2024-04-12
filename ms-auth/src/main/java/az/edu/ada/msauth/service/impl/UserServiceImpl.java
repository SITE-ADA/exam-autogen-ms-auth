package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.mapper.InstitutionRepresentativeMapper;
import az.edu.ada.msauth.mapper.InstructorMapper;
import az.edu.ada.msauth.model.dto.InstitutionRepresentativeDetailsDTO;
import az.edu.ada.msauth.model.dto.InstructorDetailsDTO;
import az.edu.ada.msauth.model.entities.*;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.repository.CustomUserDetailsRepository;
import az.edu.ada.msauth.repository.InstitutionRepository;
import az.edu.ada.msauth.repository.UserRepository;
import az.edu.ada.msauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static az.edu.ada.msauth.util.DateConverter.convertStringToLocalDate;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final CustomUserDetailsRepository customUserDetailsRepository;
    private final InstitutionRepository institutionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ContactRepository contactRepository,
                           CustomUserDetailsRepository customUserDetailsRepository,
                           InstitutionRepository institutionRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.customUserDetailsRepository = customUserDetailsRepository;
        this.institutionRepository = institutionRepository;
    }

    @Override
    public List<InstitutionRepresentativeDetailsDTO> getInstitutionRepresentativesByInstitutionId(Long institutionId, Long userTypeId) {
        List<User> institutionRepresentatives = userRepository.findInstructorsByInstitutionIdAndUserTypeId(institutionId, userTypeId);

        return institutionRepresentatives.stream()
                .map(user -> {
                    return InstitutionRepresentativeMapper.INSTANCE.toInstitutionRepresentativeDTOWithDetails(user, customUserDetailsRepository, contactRepository);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InstructorDetailsDTO> getInstructorsByInstitutionId(Long institutionId, Long userTypeId) {
        List<User> instructors = userRepository.findInstructorsByInstitutionIdAndUserTypeId(institutionId, userTypeId);

        return instructors.stream()
                .map(user -> {
                    return InstructorMapper.INSTANCE.toInstructorDTOWithDetails(user, customUserDetailsRepository, contactRepository);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User patchUser(Long id, Map<String, Object> updates) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        applyPatchToUser(user, updates);
        userRepository.save(user);
        return user;
    }

    private void applyPatchToUser(User user, Map<String, Object> updates) {
        Class<?> clazz = user.getClass();
        updates.forEach((key, value) -> {
            try {
                if (key.equals("institutionId")) {
                    handleInstitutionUpdate(user, value);
                } else {
                    Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);
                    Object val = value;
                    if (key.equals("userTypeId")) {
                        val = Long.parseLong(value.toString());
                    }
                    field.set(user, val);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle the exception, possibly logging a warning or throwing a custom exception
            }
        });
    }

    private void handleInstitutionUpdate(User user, Object value) {
        if (value == null) {
            user.setInstitution(null);
        } else {
            Long institutionId = Long.parseLong(value.toString());
            Institution institution = institutionRepository.findById(institutionId)
                    .orElseThrow(() -> new RuntimeException("Institution not found"));
            user.setInstitution(institution);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
