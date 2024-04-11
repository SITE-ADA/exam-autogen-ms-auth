package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.entities.Contact;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ContactService {
    Contact createContact(Contact contact);
    List<Contact> getAllContacts();
    Optional<Contact> getContactById(Long id);
    Contact updateContact(Long id, Contact updatedContact);
    Contact patchContact(Long id, Map<String,Object> updates);
    void deleteContact(Long id);
}
