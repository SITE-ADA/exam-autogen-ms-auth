package az.edu.ada.msauth.model.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class InstructorDetailsDTO {
    private Long instructorID;
    private String fullName;
    private String username;
    private LocalDate birthdate;
    private ContactDTO contactDTO;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ContactDTO {
        private String primaryPhone;
        private String primaryEmail;
        private String secondaryPhone;
        private String secondaryEmail;
    }
}