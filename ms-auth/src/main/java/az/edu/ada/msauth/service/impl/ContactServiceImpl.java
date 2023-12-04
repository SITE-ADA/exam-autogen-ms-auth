package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact updateContact(Long id, Contact updatedContact) {
        Optional<Contact> existingContactOptional = contactRepository.findById(id);

        if (existingContactOptional.isPresent()) {
            Contact existingContact = existingContactOptional.get();

            updatedContact.setCreatedAt(existingContact.getCreatedAt());

            updatedContact.setId(id);

            return contactRepository.save(updatedContact);
        } else {
            return null;
        }
    }

    @Override
    public Contact patchContact(Long id, Map<String, Object> updates) {
        Optional<Contact> existingContactOptional = contactRepository.findById(id);
        if (existingContactOptional.isPresent()){
            Contact existingContact = existingContactOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "primaryPhone" -> {
                        existingContact.setPrimaryPhone((String) value);
                        break;
                    }
                    case "secondaryPhone" -> {
                        existingContact.setSecondaryPhone((String) value);
                        break;
                    }
                    case "primaryEmail" -> {
                        existingContact.setPrimaryEmail((String) value);
                        break;
                    }
                    case "secondaryEmail" -> {
                        existingContact.setSecondaryEmail((String) value);
                        break;
                    }
                    default -> {
                        break;
                    }
                }
            });

            existingContact.setId(id);

            return contactRepository.save(existingContact);
        } else {
            return null;
        }
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
