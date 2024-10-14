package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonDTOForm {
    @NotNull(message = "Person Id is required")
    @PositiveOrZero(message = "Id cannot hold negative value")
    private Long id;

    @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
    private String name;

    @Valid
    private List<TaskDTOForm> taskList;

    @Valid
    private UserDTOForm user;

    public PersonDTOForm(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PersonDTOForm(Long id, String name, List<TaskDTOForm> taskList) {
        this.id = id;
        this.name = name;
        this.taskList = taskList;
    }
}
