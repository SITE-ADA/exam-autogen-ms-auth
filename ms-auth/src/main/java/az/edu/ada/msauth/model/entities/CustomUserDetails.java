package az.edu.ada.msauth.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_details")
public class CustomUserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dob;

    @JoinColumn(name = "address_id")
    private Long addressId;

    @JoinColumn(name = "contact_id")
    private Long contactId;

    @JoinColumn(name = "user_id")
    private Long userId;
}
