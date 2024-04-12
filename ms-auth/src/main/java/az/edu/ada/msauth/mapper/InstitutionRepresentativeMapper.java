package az.edu.ada.msauth.mapper;

import az.edu.ada.msauth.model.dto.InstitutionRepresentativeDetailsDTO;
import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.model.entities.CustomUserDetails;
import az.edu.ada.msauth.model.entities.User;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.repository.CustomUserDetailsRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface InstitutionRepresentativeMapper {

    InstitutionRepresentativeMapper INSTANCE = Mappers.getMapper(InstitutionRepresentativeMapper.class);

    InstitutionRepresentativeDetailsDTO toInstitutionRepresentativeDetailsDTO(User user);

    default InstitutionRepresentativeDetailsDTO toInstitutionRepresentativeDTOWithDetails(User user,
                                                            CustomUserDetailsRepository userDetailsRepo,
                                                            ContactRepository contactRepo) {
        InstitutionRepresentativeDetailsDTO institutionRepresentativeDTO = toInstitutionRepresentativeDetailsDTO(user);

        Optional<CustomUserDetails> customUserDetails = userDetailsRepo.findByUserId(user.getId());

        if (customUserDetails.isPresent()) {
            Contact contact = contactRepo.findById(customUserDetails.get().getContactId())
                    .orElseThrow();
            InstitutionRepresentativeDetailsDTO.ContactDTO contactDTO = new InstitutionRepresentativeDetailsDTO.ContactDTO();
            contactDTO.setPrimaryEmail(contact.getPrimaryEmail());
            contactDTO.setPrimaryPhone(contact.getPrimaryPhone());
            contactDTO.setSecondaryEmail(contact.getSecondaryEmail());
            contactDTO.setSecondaryPhone(contact.getSecondaryPhone());

            institutionRepresentativeDTO.setInstitutionRepresentativeID(user.getId());
            institutionRepresentativeDTO.setFullName(customUserDetails.get().getFirstName() + " " + customUserDetails.get().getLastName());
            institutionRepresentativeDTO.setUsername(user.getUsername());
            institutionRepresentativeDTO.setBirthdate(customUserDetails.get().getDob());
            institutionRepresentativeDTO.setContactDTO(contactDTO);
        }

        return institutionRepresentativeDTO;
    }

}
