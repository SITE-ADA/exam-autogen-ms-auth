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
    private final UserTypeService userTypeService;

    @PostMapping
    public ResponseEntity<UserType> createUserType(@RequestBody UserType userType) {

        UserType createdUserType =  userTypeService.createUserType(userType);
        return ResponseEntity.ok(createdUserType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserType> getUserTypeById(@PathVariable Long id) {
        return userTypeService.getUserTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserType>> getAllUserTypes() {
        List<UserType> userTypes = userTypeService.getAllUserTypes();
        return ResponseEntity.ok(userTypes);
    }

    @PutMapping("/{id}")
    public UserType updateUserType(@PathVariable Long id, @RequestBody UserType userType) {
        return userTypeService.updateUserType(id, userType);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserType> patchUserType(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        UserType patchedUserType = userTypeService.patchUserType(id, updates);
        if (patchedUserType != null) {
            return ResponseEntity.ok(patchedUserType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserType(@PathVariable Long id) {
        userTypeService.deleteUserType(id);
        return ResponseEntity.noContent().build();
    }
}
