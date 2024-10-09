package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "taskList")
@Builder
public class PersonDTOFormView {
    private Long id;
    private String name;
    private List<TaskDTOFormView> taskList;
    private UserDTOView user;
}
