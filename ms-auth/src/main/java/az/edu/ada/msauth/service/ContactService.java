package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.entities.Contact;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ContactService {
    public List<Contact> getAllContacts();
    public Contact createContact(Contact contact);
    public Optional<Contact> getContactById(Long id);
    public Contact updateContact(Long id, Contact updatedContact);
    public Contact patchContact(Long id, Map<String,Object> updates);
    public void deleteContact(Long id);
}
