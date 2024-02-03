package az.edu.ada.msauth.service;

import az.edu.ada.msauth.auth.AuthenticationRequest;
import az.edu.ada.msauth.auth.RegisterRequest;
import az.edu.ada.msauth.model.dto.AuthenticationResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<Object> register(RegisterRequest request);
    AuthenticationResponseDTO authenticate(AuthenticationRequest request);
}
