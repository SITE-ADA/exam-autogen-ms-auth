package az.edu.ada.msauth.service;

import az.edu.ada.msauth.auth.AuthenticationRequest;
import az.edu.ada.msauth.auth.AuthenticationResponse;
import az.edu.ada.msauth.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<Object> register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
