package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTOView {
    private String email;
    private Set<RoleDTOFormView> roles;
}
