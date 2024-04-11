package az.edu.ada.msauth.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "institution",
        uniqueConstraints = {
                @UniqueConstraint(name = "name_unique", columnNames = "institution_name")
        })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "contact_id")
    private Long contactId;

    @NotBlank
    private String status;
}
