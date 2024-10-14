package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonDTOFormSave {
    @NotBlank(message = "Person name is required")
    @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
    private String name;

    @NotNull(message = "User cannot be null")
    @Valid
    private UserDTOForm user;
}
