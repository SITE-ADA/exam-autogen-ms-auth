package az.edu.ada.msauth.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contact",
        uniqueConstraints = {
            @UniqueConstraint(name = "primary_phone_unique", columnNames = "primary_phone"),
            @UniqueConstraint(name = "primary_email_unique", columnNames = "primary_email")
        })
public class Contact {
    @OneToOne(mappedBy = "contact")
    private UserDetails userDetails;
    @OneToOne(mappedBy = "contact")
    private Institution institution;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String primaryPhone;
    private String secondaryPhone;
    @NotBlank
    @Email
    private String primaryEmail;
    @Email
    private String secondaryEmail;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
