package az.edu.ada.msauth.controller;

import az.edu.ada.msauth.model.entities.UserType;
import az.edu.ada.msauth.service.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/user-types")
public class UserTypeController {
    private final UserTypeService UserTypeService;

    @PostMapping
    public ResponseEntity<UserType> createUserType(@RequestBody UserType UserType) {

        UserType createdUserType =  UserTypeService.createUserType(UserType);
        return ResponseEntity.ok(createdUserType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserType> getUserTypeById(@PathVariable Long id) {
        return UserTypeService.getUserTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserType>> getAllUserTypes() {
        List<UserType> UserTypes = UserTypeService.getAllUserTypes();
        return ResponseEntity.ok(UserTypes);
    }

    @PutMapping("/{id}")
    public UserType updateUserType(@PathVariable Long id, @RequestBody UserType UserType) {
        return UserTypeService.updateUserType(id, UserType);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserType> patchUserType(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        UserType patchedUserType = UserTypeService.patchUserType(id, updates);
        if (patchedUserType != null) {
            return ResponseEntity.ok(patchedUserType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserType(@PathVariable Long id) {
        UserTypeService.deleteUserType(id);
        return ResponseEntity.noContent().build();
    }
}
