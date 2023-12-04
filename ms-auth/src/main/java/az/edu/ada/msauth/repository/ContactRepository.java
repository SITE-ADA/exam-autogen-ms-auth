package az.edu.ada.msauth.repository;

import az.edu.ada.msauth.model.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
