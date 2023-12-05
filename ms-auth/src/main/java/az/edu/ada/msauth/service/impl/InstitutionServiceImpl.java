package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.model.entities.Institution;
import az.edu.ada.msauth.repository.InstitutionRepository;
import az.edu.ada.msauth.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionServiceImpl implements InstitutionService {
    private final InstitutionRepository institutionRepository;

    @Autowired
    public InstitutionServiceImpl(InstitutionRepository institutionRepository){
        this.institutionRepository = institutionRepository;
    }

    @Override
    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }
    @Override
    public Institution createInstitution(Institution institution) {
        return institutionRepository.save(institution);
    }

    @Override
    public Institution getInstitutionById(Long id) {
        Optional<Institution> optionalInstitution = institutionRepository.findById(id);
        return optionalInstitution.orElse(null);
    }

    @Override
    public Institution updateInstitution(Long id, Institution updatedInstitution) {
        Institution existingInstitution = getInstitutionById(id);
        if (existingInstitution != null) {
            existingInstitution.setAddress(updatedInstitution.getAddress());
            existingInstitution.setContact(updatedInstitution.getContact());
            existingInstitution.setStatus(updatedInstitution.getStatus());
            return institutionRepository.save(existingInstitution);
        }
        return null;
    }

    @Override
    public void deleteInstitution(Long id) {
        institutionRepository.deleteById(id);
    }

}
