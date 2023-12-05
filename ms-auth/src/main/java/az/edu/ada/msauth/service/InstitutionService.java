package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.entities.Institution;

import java.util.List;
import java.util.Optional;

public interface InstitutionService {
    Institution createInstitution(Institution institution);
    Institution getInstitutionById(Long id);
    Institution updateInstitution(Long id, Institution institution);
    void deleteInstitution(Long id);
    List<Institution> getAllInstitutions();
}
