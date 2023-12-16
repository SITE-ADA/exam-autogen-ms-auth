package az.edu.ada.msauth.controller;

import az.edu.ada.msauth.model.entities.Address;
import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.model.entities.User;
import az.edu.ada.msauth.model.entities.UserDetails;
import az.edu.ada.msauth.repository.UserDetailsRepository;
import az.edu.ada.msauth.repository.UserRepository;
import az.edu.ada.msauth.service.AddressService;
import az.edu.ada.msauth.service.ContactService;
import az.edu.ada.msauth.service.impl.AddressServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/details")
@RequiredArgsConstructor
public class UserDetailsController {
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final UserDetailsService userDetailsService;
    private final AddressService addressService;
    private final ContactService contactService;
    private final AddressServiceImpl addressServiceImpl;

    @PostMapping("/create-address")
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address createdAddress = addressServiceImpl.createAddress(address);
        return ResponseEntity.ok(createdAddress);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDetails> updateUserDetails(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<UserDetails> existingUserDetails = userDetailsRepository.findById(id);
        if (existingUserDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userDetails = existingUserDetails.get();
        var foundUserDetails = userDetailsRepository.findById(id).get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName" -> userDetails.setFirstName((String) value);
                case "lastName" -> userDetails.setLastName((String) value);
                case "dob" -> userDetails.setDob(LocalDate.parse((String) value));
                case "address" -> {
                    Integer intVal = (Integer) value;
                    Address address = addressService.getAddressById(intVal.longValue()).get();
                    userDetails.setAddress(address);
                }
                case "contact" -> {
                    Contact contact = foundUserDetails.getContact();
                    Contact createdContact =  mapToObject(value, Contact.class);

                    contact.setPrimaryEmail(createdContact.getPrimaryEmail());
                    contact.setPrimaryPhone(createdContact.getPrimaryPhone());
                    contact.setSecondaryEmail(createdContact.getSecondaryEmail());
                    contact.setSecondaryPhone(createdContact.getSecondaryPhone());

                    contactService.updateContact(contact.getId(), contact);
                    userDetails.setContact(contact);
                }
                default -> {
                }
            }
        });

        userDetails.setId(id);

        UserDetails savedUserDetails = userDetailsRepository.save(userDetails);

        return ResponseEntity.ok(savedUserDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable Long id) {
        if (!userDetailsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private <T> T mapToObject(Object source, Class<T> targetType) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(source, targetType);
    }
}

