package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonDTOForm {
    private Long id;
    private String name;
    private List<TaskDTOForm> taskList;
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
