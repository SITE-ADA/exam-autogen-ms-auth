package az.edu.ada.msauth.mapper;

import az.edu.ada.msauth.model.dto.InstructorDetailsDTO;
import az.edu.ada.msauth.model.entities.CustomUserDetails;
import az.edu.ada.msauth.model.entities.User;
import az.edu.ada.msauth.model.entities.Contact;
import az.edu.ada.msauth.repository.ContactRepository;
import az.edu.ada.msauth.repository.CustomUserDetailsRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface InstructorMapper {

    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    InstructorDetailsDTO toInstructorDetailsDTO(User user);

    default InstructorDetailsDTO toInstructorDTOWithDetails(User user,
                                                            CustomUserDetailsRepository userDetailsRepo,
                                                            ContactRepository contactRepo) {
        InstructorDetailsDTO instructorDTO = toInstructorDetailsDTO(user);

        Optional<CustomUserDetails> customUserDetails = userDetailsRepo.findByUserId(user.getId());

        if (customUserDetails.isPresent()) {
            Contact contact = contactRepo.findById(customUserDetails.get().getContactId())
                    .orElseThrow();
            InstructorDetailsDTO.ContactDTO contactDTO = new InstructorDetailsDTO.ContactDTO();
            contactDTO.setPrimaryEmail(contact.getPrimaryEmail());
            contactDTO.setPrimaryPhone(contact.getPrimaryPhone());
            contactDTO.setSecondaryEmail(contact.getSecondaryEmail());
            contactDTO.setSecondaryPhone(contact.getSecondaryPhone());

            instructorDTO.setInstructorID(user.getId());
            instructorDTO.setFullName(customUserDetails.get().getFirstName() + " " + customUserDetails.get().getLastName());
            instructorDTO.setUsername(user.getUsername());
            instructorDTO.setBirthdate(customUserDetails.get().getDob());
            instructorDTO.setContactDTO(contactDTO);
        }

        return instructorDTO;
    }

}
