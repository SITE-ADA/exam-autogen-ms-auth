package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.mapper.InstitutionRepresentativeMapper;
import az.edu.ada.msauth.mapper.InstructorMapper;
import az.edu.ada.msauth.model.dto.InstitutionRepresentativeDetailsDTO;
import az.edu.ada.msauth.model.dto.InstructorDetailsDTO;
import az.edu.ada.msauth.model.entities.*;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.repository.CustomUserDetailsRepository;
import az.edu.ada.msauth.repository.UserRepository;
import az.edu.ada.msauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final CustomUserDetailsRepository customUserDetailsRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ContactRepository contactRepository,
                           CustomUserDetailsRepository customUserDetailsRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.customUserDetailsRepository = customUserDetailsRepository;
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
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
