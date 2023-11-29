package az.edu.ada.msauth.model.entities;

import jakarta.persistence.*;
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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Creates unique ID each time
    private Long id;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String zip;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "address")
    private UserDetails userDetails;
    @OneToOne(mappedBy = "address")
    private Institution institution;

}
