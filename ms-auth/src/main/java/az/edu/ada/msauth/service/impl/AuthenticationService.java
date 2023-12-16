package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.auth.AuthenticationRequest;
import az.edu.ada.msauth.auth.AuthenticationResponse;
import az.edu.ada.msauth.auth.RegisterRequest;
import az.edu.ada.msauth.exception.UserNotFoundException;
import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.model.entities.ERole;
import az.edu.ada.msauth.model.entities.User;
import az.edu.ada.msauth.model.entities.UserDetails;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.repository.UserDetailsRepository;
import az.edu.ada.msauth.repository.UserRepository;
import az.edu.ada.msauth.security.jwt.JwtUtils;
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
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final ContactRepository contactRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtil;
    private final AuthenticationManager authenticationManager;
    public ResponseEntity<Object> register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(ERole.INSTITUTION_REPRESENTATIVE)
                .build();

        var contact = Contact.builder()
                .primaryEmail(request.getEmail())
                .primaryPhone(request.getPhone())
                .build();

        var userDetails = UserDetails.builder()
                .user(user)
                .contact(contact)
                .build();

        userRepository.save(user);
        contactRepository.save(contact);
        userDetailsRepository.save(userDetails);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found or credentials are invalid"));

        if(user.getRole().toString().isEmpty()) {
            user.setRole(ERole.INSTITUTION_REPRESENTATIVE);
        }

        var jwtToken = jwtUtil.generateJwtToken(authentication);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
