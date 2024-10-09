package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TaskDTOFormSave {
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private PersonDTOForm person;
}
