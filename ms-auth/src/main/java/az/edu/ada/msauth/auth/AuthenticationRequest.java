package az.edu.ada.msauth.auth;

import az.edu.ada.msauth.model.entities.ERole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class AuthenticationRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
