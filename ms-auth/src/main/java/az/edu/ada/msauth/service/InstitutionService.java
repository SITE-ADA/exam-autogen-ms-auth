package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.entities.Institution;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InstitutionService {
    Institution createInstitution(Institution institution);
    Institution getInstitutionById(Long id);
    Institution updateInstitution(Long id, Institution institution);
    Institution patchInstitution(Long id, Map<String, Object> updates);
    void deleteInstitution(Long id);
    List<Institution> getAllInstitutions();
}
