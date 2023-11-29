package az.edu.ada.msauth.controller;

import az.edu.ada.msauth.auth.AuthenticationRequest;
import az.edu.ada.msauth.auth.AuthenticationResponse;
import az.edu.ada.msauth.auth.RegisterRequest;
import az.edu.ada.msauth.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public  ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request)
    {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
