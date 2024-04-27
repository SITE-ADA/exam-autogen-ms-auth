package az.edu.ada.msauth.mapper;

import az.edu.ada.msauth.model.dto.InstitutionDetailsDTO;
import az.edu.ada.msauth.model.entities.Address;
import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.model.entities.Institution;
import org.mapstruct.Mapper;

@Mapper
public interface InstitutionMapper {

    InstitutionDetailsDTO toInstitutionDetailsDTO(Institution institution);

    // Assuming Address and Contact are fetched and passed separately
    default InstitutionDetailsDTO toInstitutionDetailsDTO(Institution institution, Address address, Contact contact) {
        InstitutionDetailsDTO dto = toInstitutionDetailsDTO(institution);
        dto.setAddress(toAddressDTO(address));
        dto.setContact(toContactDTO(contact));
        return dto;
    }

    InstitutionDetailsDTO.AddressDTO toAddressDTO(Address address);

    InstitutionDetailsDTO.ContactDTO toContactDTO(Contact contact);
}