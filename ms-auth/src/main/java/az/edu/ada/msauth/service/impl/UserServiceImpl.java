package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.mapper.InstructorMapper;
import az.edu.ada.msauth.model.dto.InstructorDetailsDTO;
import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.model.entities.CustomUserDetails;
import az.edu.ada.msauth.model.entities.User;
import az.edu.ada.msauth.model.entities.UserDetailsImpl;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.repository.CustomUserDetailsRepository;
import az.edu.ada.msauth.repository.UserRepository;
import az.edu.ada.msauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
