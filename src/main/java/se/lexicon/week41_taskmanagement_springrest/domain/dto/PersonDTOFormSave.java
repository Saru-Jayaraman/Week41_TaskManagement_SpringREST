package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonDTOFormSave {
    private String name;
    private UserDTOForm user;
}
