package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
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
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact updateContact(Long id, Contact updatedContact) {
        Optional<Contact> existingContactOptional = contactRepository.findById(id);

        if (existingContactOptional.isPresent()) {
            updatedContact.setId(id);
            return contactRepository.save(updatedContact);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Contact patchContact(Long id, Map<String, Object> updates) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (!optionalContact.isPresent()) {
            return null;
        }

        Contact contact = optionalContact.get();
        applyPatchToContact(contact, updates);
        contactRepository.save(contact);
        return contact;
    }

    private void applyPatchToContact(Contact contact, Map<String, Object> updates) {
        Class<?> clazz = contact.getClass();
        updates.forEach((key, value) -> {
            try {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(contact, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle the exception, possibly logging a warning or throwing a custom exception
            }
        });
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
