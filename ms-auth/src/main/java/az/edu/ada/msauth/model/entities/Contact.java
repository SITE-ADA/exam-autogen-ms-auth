package az.edu.ada.msauth.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Valid
@Builder
@Table(name = "contact",
        uniqueConstraints = {
            @UniqueConstraint(name = "primary_phone_unique", columnNames = "primary_phone"),
            @UniqueConstraint(name = "primary_email_unique", columnNames = "primary_email")
        })
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "primary_phone")
    private String primaryPhone;

    @NotBlank
    @Email
    @Column(name = "primary_email")
    private String primaryEmail;

    private String secondaryPhone;
    @Email
    private String secondaryEmail;
}
