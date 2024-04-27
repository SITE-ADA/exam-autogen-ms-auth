package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.dto.InstitutionDetailsDTO;
import az.edu.ada.msauth.model.entities.Institution;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InstitutionService {
    Institution createInstitution(Institution institution);
    Optional<Institution> getInstitutionById(Long id);
    Institution updateInstitution(Long id, Institution institution);
    Institution patchInstitution(Long id, Map<String, Object> updates);
    void deleteInstitution(Long id);

    InstitutionDetailsDTO getInstitutionDetails(Long institutionId);

    List<InstitutionDetailsDTO> getAllInstitutionDetails();

    List<Institution> getAllInstitutions();
}
