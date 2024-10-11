package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTOForm {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lower case letter, one number and one special character")
    private String password;

    @NotNull(message = "Roles cannot be null")
    @Size(min = 1, message = "User must have at least one role")
    @Valid
    private Set<RoleDTOForm> roles;
}
