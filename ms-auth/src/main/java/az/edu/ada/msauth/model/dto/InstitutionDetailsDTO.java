package az.edu.ada.msauth.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstitutionDetailsDTO {

    private Long id;
    private String institutionName;
    private AddressDTO address;
    private ContactDTO contact;
    private String status;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AddressDTO {
        private Long id;
        private String country;
        private String city;
        private String street;
        private String zip;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ContactDTO {
        private Long id;
        private String primaryPhone;
        private String primaryEmail;
        private String secondaryPhone;
        private String secondaryEmail;
    }
}
