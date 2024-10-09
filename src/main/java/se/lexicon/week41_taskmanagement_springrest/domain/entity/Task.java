package se.lexicon.week41_taskmanagement_springrest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "person")
@EqualsAndHashCode(exclude = "person")
@Builder
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    @Setter private String title;

    @Column(length = 100)
    @Setter private String description;

    @Setter private LocalDate deadLine;

    @Setter private boolean done;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @Setter private Person person;

    public Task(String title, String description, LocalDate deadLine, boolean done) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
    }

    public Task(String title, String description, LocalDate deadLine, boolean done, Person person) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
        this.person = person;
    }

    public Task(String title, String description, LocalDate deadLine) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = false;
    }
}
