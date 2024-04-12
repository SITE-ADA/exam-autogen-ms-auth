package az.edu.ada.msauth.controller;

import az.edu.ada.msauth.model.entities.CustomUserDetails;
import az.edu.ada.msauth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/user-details")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomUserDetailsController {
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public ResponseEntity<List<CustomUserDetails>> getAllCustomUserDetails(){
        List<CustomUserDetails> customUserDetails = customUserDetailsService.getAllCustomUserDetails();
        return ResponseEntity.ok(customUserDetails);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomUserDetails> getCustomUserDetailsById(@PathVariable Long userId){
        return customUserDetailsService.getCustomUserDetailsByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomUserDetails> updateCustomUserDetails(@PathVariable Long id, @RequestBody CustomUserDetails updateCustomUserDetails){
        CustomUserDetails updated = customUserDetailsService.updateCustomUserDetails(id, updateCustomUserDetails);
        if (updated != null){
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomUserDetails> patchCustomUserDetails(@PathVariable Long id, @RequestBody Map<String, Object> updates){
        CustomUserDetails patchedCustomUserDetails = customUserDetailsService.patchCustomUserDetails(id, updates);
        if (patchedCustomUserDetails != null) {
            return ResponseEntity.ok(patchedCustomUserDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        customUserDetailsService.deleteCustomUserDetails(id);
        return ResponseEntity.noContent().build();
    }
}

