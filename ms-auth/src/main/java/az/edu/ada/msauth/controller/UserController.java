package az.edu.ada.msauth.controller;

import az.edu.ada.msauth.model.dto.InstitutionRepresentativeDetailsDTO;
import az.edu.ada.msauth.model.dto.InstructorDetailsDTO;
import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.model.entities.User;
import az.edu.ada.msauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @GetMapping("/inst-representatives")
    public List<InstitutionRepresentativeDetailsDTO> getInstitutionRepresentativesByInstitutionAndUserType(
            @RequestParam Long institutionId,
            @RequestParam Long userTypeId) {
        return userService.getInstitutionRepresentativesByInstitutionId(institutionId, userTypeId);
    }

    @GetMapping("/instructors")
    public List<InstructorDetailsDTO> getInstructorsByInstitutionAndUserType(
            @RequestParam Long institutionId,
            @RequestParam Long userTypeId) {
        return userService.getInstructorsByInstitutionId(institutionId, userTypeId);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates){
        User patchedUser = userService.patchUser(id, updates);
        if (patchedUser != null) {
            return ResponseEntity.ok(patchedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
