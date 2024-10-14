package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TaskDTOFormSave {
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @Size(max = 100, message = "Title can have a max of 100 characters")
    private String description;

    @NotNull(message = "Deadline is required")
    private LocalDate deadLine;

    private boolean done;

    @Valid
    private PersonDTOForm person;
}
