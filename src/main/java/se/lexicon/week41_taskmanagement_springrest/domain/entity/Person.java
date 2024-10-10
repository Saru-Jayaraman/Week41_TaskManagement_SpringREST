package se.lexicon.week41_taskmanagement_springrest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @Setter private String name;

    @OneToMany(mappedBy = "person")
    @Setter private List<Task> taskList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email")
    @Setter private User user;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void addTask(Task... tasks) {
        if(Objects.requireNonNull(tasks).length == 0)
            throw new IllegalArgumentException("Tasks are either null/empty...");
        for(Task eachTask : tasks) {
            if(eachTask.getPerson() == null)
                eachTask.setPerson(this);
            if(this.taskList == null)
                this.taskList = new ArrayList<>();
            this.taskList.add(eachTask);
        }
    }

    public void removeTask(Task... tasks) {
        if(Objects.requireNonNull(tasks).length == 0)
            throw new IllegalArgumentException("Tasks are either null/empty...");
        for(Task eachTask : tasks) {
            if (this.taskList.remove(eachTask) && eachTask.getPerson() == this) {
                eachTask.setPerson(null);
            }
        }
    }
}
