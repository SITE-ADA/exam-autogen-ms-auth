package az.edu.ada.msauth.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsDTO {
    private Long userID;
    private String fullName;
    private String username;
    private InstitutionDTO institutionDTO;
    private ContactDTO contactDTO;
    private AddressDTO addressDTO;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ContactDTO {
        private String primaryPhone;
        private String primaryEmail;
        private String secondaryPhone;
        private String secondaryEmail;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AddressDTO {
        private String country;
        private String city;
        private String street;
        private String zip;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class InstitutionDTO {
        private String institutionName;
        private AddressDTO addressDTO;
        private ContactDTO contactDTO;
        private String status;
    }
}
