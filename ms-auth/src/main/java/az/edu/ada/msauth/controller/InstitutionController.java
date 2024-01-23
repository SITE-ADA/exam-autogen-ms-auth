package az.edu.ada.msauth.controller;

import az.edu.ada.msauth.model.entities.Address;
import az.edu.ada.msauth.model.entities.Institution;
import az.edu.ada.msauth.service.InstitutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/institution")
public class InstitutionController {
    private final InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @PostMapping
    public ResponseEntity<Institution> createInstitution(@RequestBody Institution institution) {

        Institution createdInstitution =  institutionService.createInstitution(institution);
        return ResponseEntity.ok(createdInstitution);
    }

    @GetMapping("/{id}")
    public Institution getInstitutionById(@PathVariable Long id) {
        return institutionService.getInstitutionById(id);
    }

    @GetMapping
    public ResponseEntity<List<Institution>> getAllInstitutions() {
        List<Institution> institutions = institutionService.getAllInstitutions();
        return ResponseEntity.ok(institutions);
    }

    @PutMapping("/{id}")
    public Institution updateInstitution(@PathVariable Long id, @RequestBody Institution institution) {
        return institutionService.updateInstitution(id, institution);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Institution> patchInstitution(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Institution patchedInstitution = institutionService.patchInstitution(id, updates);
        if (patchedInstitution != null) {
            return ResponseEntity.ok(patchedInstitution);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        institutionService.deleteInstitution(id);
        return ResponseEntity.noContent().build();
    }
}
