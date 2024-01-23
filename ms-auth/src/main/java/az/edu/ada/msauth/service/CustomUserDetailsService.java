package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.entities.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomUserDetailsService extends UserDetailsService {
    List<CustomUserDetails> getAllCustomUserDetails();
    Optional<CustomUserDetails> getCustomUserDetailsById(Long id);
    CustomUserDetails updateCustomUserDetails(Long id, CustomUserDetails updatedCustomUserDetails);
    CustomUserDetails patchCustomUserDetails(Long id, Map<String,Object> updates);
    void deleteCustomUserDetails(Long id);
}
