package az.edu.ada.msauth.service.impl;


import az.edu.ada.msauth.model.entities.*;
import az.edu.ada.msauth.repository.CustomUserDetailsRepository;
import az.edu.ada.msauth.repository.UserRepository;
import az.edu.ada.msauth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static az.edu.ada.msauth.util.DateConverter.convertStringToLocalDate;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    UserRepository userRepository;
    CustomUserDetailsRepository customUserDetailsRepository;

    @Autowired
    CustomUserDetailsServiceImpl(UserRepository userRepository, CustomUserDetailsRepository customUserDetailsRepository) {
        this.userRepository = userRepository;
        this.customUserDetailsRepository = customUserDetailsRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    @Override
    public List<CustomUserDetails> getAllCustomUserDetails() {
        return customUserDetailsRepository.findAll();
    }

    @Override
    public Optional<CustomUserDetails> getCustomUserDetailsById(Long id) {
        System.out.println(customUserDetailsRepository.findById(id));
        return customUserDetailsRepository.findById(id);
    }

    @Override
    public CustomUserDetails updateCustomUserDetails(Long id, CustomUserDetails updatedCustomUserDetails) {
        Optional<CustomUserDetails> existingCustomUserDetailsRepository = customUserDetailsRepository.findById(id);

        if (existingCustomUserDetailsRepository.isPresent()) {
            updatedCustomUserDetails.setId(id);
            return customUserDetailsRepository.save(updatedCustomUserDetails);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public CustomUserDetails patchCustomUserDetails(Long id, Map<String, Object> updates) {
        Optional<CustomUserDetails> optionalCustomUserDetails = customUserDetailsRepository.findById(id);
        if (!optionalCustomUserDetails.isPresent()) {
            return null;
        }

        CustomUserDetails customUserDetails = optionalCustomUserDetails.get();
        applyPatchToCustomUserDetails(customUserDetails, updates);
        customUserDetailsRepository.save(customUserDetails);
        return customUserDetails;
    }

    private void applyPatchToCustomUserDetails(CustomUserDetails customUserDetails, Map<String, Object> updates) {
        Class<?> clazz = customUserDetails.getClass();
        updates.forEach((key, value) -> {
            try {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                var val = value;
                if(key.equals("dob")) {
                   val = convertStringToLocalDate((String) value);
                } else if(key.equals("addressId") || key.equals("contactId")) {
                    val = Long.parseLong(value.toString());
                }
                field.set(customUserDetails, val);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle the exception, possibly logging a warning or throwing a custom exception
            }
        });
    }

    @Override
    public void deleteCustomUserDetails(Long id) {
        customUserDetailsRepository.deleteById(id);
    }
}