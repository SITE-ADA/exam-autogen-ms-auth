package az.edu.ada.msauth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long id;
    private String name;
    private String crn;
    private String term;
    private Long status;
    private String courseObjectives;
    private String description;
}