package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.mapper.InstitutionMapper;
import az.edu.ada.msauth.model.dto.InstitutionDetailsDTO;
import az.edu.ada.msauth.model.entities.*;
import az.edu.ada.msauth.repository.*;
import az.edu.ada.msauth.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstitutionServiceImpl implements InstitutionService {
    private final InstitutionRepository institutionRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final InstitutionMapper institutionMapper;

    @Autowired
    public InstitutionServiceImpl(InstitutionRepository institutionRepository,
                                  AddressRepository addressRepository,
                                  ContactRepository contactRepository,
                                  InstitutionMapper institutionMapper) {
        this.institutionRepository = institutionRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
        this.institutionMapper = institutionMapper;
    }

    @Override
    public InstitutionDetailsDTO getInstitutionDetails(Long institutionId) {
        Institution institution = institutionRepository.findById(institutionId).orElseThrow(/* exception */);
        Address address = addressRepository.findById(institution.getAddressId()).orElseThrow(/* exception */);
        Contact contact = contactRepository.findById(institution.getContactId()).orElseThrow(/* exception */);

        return institutionMapper.toInstitutionDetailsDTO(institution, address, contact);
    }

    @Override
    public List<InstitutionDetailsDTO> getAllInstitutionDetails() {
        List<Institution> institutions = institutionRepository.findAll(); // Assuming you have a method to fetch all institutions
        return institutions.stream()
                .map(institution -> {
                    Address address = addressRepository.findById(institution.getAddressId()).orElse(null);
                    Contact contact = contactRepository.findById(institution.getContactId()).orElse(null);
                    return institutionMapper.toInstitutionDetailsDTO(institution, address, contact);
                })
                .collect(Collectors.toList());
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
    public Optional<Institution> getInstitutionById(Long id) {
        return institutionRepository.findById(id);
    }

    @Override
    public Institution updateInstitution(Long id, Institution updatedInstitution) {
        Optional<Institution> existingInstitutionOptional = getInstitutionById(id);
        if (existingInstitutionOptional.isPresent()) {
            updatedInstitution.setId(id);
            return institutionRepository.save(updatedInstitution);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Institution patchInstitution(Long id, Map<String, Object> updates) {
        Optional<Institution> optionalInstitution = institutionRepository.findById(id);
        if (optionalInstitution.isEmpty()) {
            return null;
        }

        Institution institution = optionalInstitution.get();
        applyPatchToInstitution(institution, updates);
        institutionRepository.save(institution);
        return institution;
    }

    @Override
    public void deleteInstitution(Long id) {
        institutionRepository.deleteById(id);
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
}
