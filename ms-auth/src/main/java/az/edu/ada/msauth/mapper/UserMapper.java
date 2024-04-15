package az.edu.ada.msauth.mapper;

import az.edu.ada.msauth.model.dto.UserDetailsDTO;
import az.edu.ada.msauth.model.entities.Institution;
import az.edu.ada.msauth.model.entities.User;
import az.edu.ada.msauth.repository.AddressRepository;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.repository.CustomUserDetailsRepository;
import az.edu.ada.msauth.repository.InstitutionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default UserDetailsDTO toUserDetailsDTO(User user,
                                            CustomUserDetailsRepository userDetailsRepository,
                                            InstitutionRepository institutionRepository, AddressRepository addressRepository,
                                            ContactRepository contactRepository) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUserID(user.getId());
        userDetailsDTO.setUsername(user.getUsername());

        // Map user's personal details
        userDetailsRepository.findByUserId(user.getId()).ifPresent(customUserDetails -> {
            userDetailsDTO.setFullName(customUserDetails.getFirstName() + " " + customUserDetails.getLastName());

            // Map user's personal contact
            contactRepository.findById(customUserDetails.getContactId()).ifPresent(contact -> {
                UserDetailsDTO.ContactDTO contactDTO = new UserDetailsDTO.ContactDTO();
                contactDTO.setPrimaryPhone(contact.getPrimaryPhone());
                contactDTO.setPrimaryEmail(contact.getPrimaryEmail());
                contactDTO.setSecondaryPhone(contact.getSecondaryPhone());
                contactDTO.setSecondaryEmail(contact.getSecondaryEmail());
                userDetailsDTO.setContactDTO(contactDTO);
            });

            // Map user's personal address
            addressRepository.findById(customUserDetails.getAddressId()).ifPresent(address -> {
                UserDetailsDTO.AddressDTO addressDTO = new UserDetailsDTO.AddressDTO();
                addressDTO.setCountry(address.getCountry());
                addressDTO.setCity(address.getCity());
                addressDTO.setStreet(address.getStreet());
                addressDTO.setZip(address.getZip());
                userDetailsDTO.setAddressDTO(addressDTO);
            });
        });

        // Map institution details if present
        Institution institution = user.getInstitution();
        if (institution != null) {
            UserDetailsDTO.InstitutionDTO institutionDTO = new UserDetailsDTO.InstitutionDTO();
            institutionDTO.setInstitutionName(institution.getInstitutionName());
            institutionDTO.setStatus(institution.getStatus());

            // Map institution's contact
            contactRepository.findById(institution.getContactId()).ifPresent(contact -> {
                UserDetailsDTO.ContactDTO contactDTO = new UserDetailsDTO.ContactDTO();
                contactDTO.setPrimaryPhone(contact.getPrimaryPhone());
                contactDTO.setPrimaryEmail(contact.getPrimaryEmail());
                contactDTO.setSecondaryPhone(contact.getSecondaryPhone());
                contactDTO.setSecondaryEmail(contact.getSecondaryEmail());
                institutionDTO.setContactDTO(contactDTO);
            });

            // Map institution's address
            addressRepository.findById(institution.getAddressId()).ifPresent(address -> {
                UserDetailsDTO.AddressDTO addressDTO = new UserDetailsDTO.AddressDTO();
                addressDTO.setCountry(address.getCountry());
                addressDTO.setCity(address.getCity());
                addressDTO.setStreet(address.getStreet());
                addressDTO.setZip(address.getZip());
                institutionDTO.setAddressDTO(addressDTO);
            });

            userDetailsDTO.setInstitutionDTO(institutionDTO);
        }

        return userDetailsDTO;
    }
}
