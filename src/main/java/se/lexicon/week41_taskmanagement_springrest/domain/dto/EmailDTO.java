package se.lexicon.week41_taskmanagement_springrest.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmailDTO {
    private String to;
    private String subject;
    private String html;
}
