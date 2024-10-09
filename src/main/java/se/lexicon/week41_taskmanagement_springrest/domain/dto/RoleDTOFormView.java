package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RoleDTOFormView {
    private Long id;
    private String name;
}
