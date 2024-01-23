package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.model.entities.Address;
import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.model.entities.Institution;
import az.edu.ada.msauth.repository.AddressRepository;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.repository.InstitutionRepository;
import az.edu.ada.msauth.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InstitutionServiceImpl implements InstitutionService {
    private final InstitutionRepository institutionRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public InstitutionServiceImpl(InstitutionRepository institutionRepository,
                                  AddressRepository addressRepository,
                                  ContactRepository contactRepository) {
        this.institutionRepository = institutionRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
    }


    @Override
    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }
    @Override
    public Institution createInstitution(Institution institution) {
        Address address = addressRepository.findById(institution.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid address ID: " + institution.getAddressId()));

        Contact contact = contactRepository.findById(institution.getContactId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact ID: " + institution.getContactId()));

        institution.setAddressId(address.getId());
        institution.setContactId(contact.getId());

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
            existingInstitution.setInstitutionName(updatedInstitution.getInstitutionName());
            existingInstitution.setAddressId(updatedInstitution.getAddressId());
            existingInstitution.setContactId(updatedInstitution.getContactId());
            existingInstitution.setStatus(updatedInstitution.getStatus());
            return institutionRepository.save(existingInstitution);
        }
        return null;
    }

    @Override
    @Transactional
    public Institution patchInstitution(Long id, Map<String, Object> updates) {
        Optional<Institution> optionalInstitution = institutionRepository.findById(id);
        if (!optionalInstitution.isPresent()) {
            return null;
        }

        Institution institution = optionalInstitution.get();
        applyPatchToInstitution(institution, updates);
        institutionRepository.save(institution);
        return institution;
    }

    private void applyPatchToInstitution(Institution institution, Map<String, Object> updates) {
        Class<?> clazz = institution.getClass();
        updates.forEach((key, value) -> {
            try {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(institution, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle the exception, possibly logging a warning or throwing a custom exception
            }
        });
    }

    @Override
    public void deleteInstitution(Long id) {
        institutionRepository.deleteById(id);
    }

}
