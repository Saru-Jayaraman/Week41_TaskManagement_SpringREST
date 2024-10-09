package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonDTOFormSave {
    private String name;
    private List<TaskDTOForm> taskList;
    private UserDTOForm user;
}
