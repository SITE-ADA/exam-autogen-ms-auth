package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.auth.AuthenticationRequest;
import az.edu.ada.msauth.auth.AuthenticationResponse;
import az.edu.ada.msauth.auth.RegisterRequest;
import az.edu.ada.msauth.exception.UserNotFoundException;
import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.model.enums.EUserType;
import az.edu.ada.msauth.model.entities.User;
import az.edu.ada.msauth.model.entities.CustomUserDetails;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.repository.CustomUserDetailsRepository;
import az.edu.ada.msauth.repository.UserRepository;
import az.edu.ada.msauth.security.jwt.JwtUtils;
import az.edu.ada.msauth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final CustomUserDetailsRepository userDetailsRepository;
    private final ContactRepository contactRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Object> register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userTypeId(2L)
                .build();

        var contact = Contact.builder()
                .primaryEmail(request.getEmail())
                .primaryPhone(request.getPhone())
                .build();

        var userDetails = CustomUserDetails.builder()
                .userId(user.getId())
                .contactId(contact.getId())
                .build();

        userRepository.save(user);
        contactRepository.save(contact);
        userDetailsRepository.save(userDetails);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found or credentials are invalid"));

        if(user.getUserTypeId().toString().isEmpty()) {
            user.setUserTypeId(2L);
        }

        var jwtToken = jwtUtil.generateJwtToken(authentication);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
