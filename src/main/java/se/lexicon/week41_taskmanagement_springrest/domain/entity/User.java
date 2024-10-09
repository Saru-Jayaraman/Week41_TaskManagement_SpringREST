package se.lexicon.week41_taskmanagement_springrest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"roles", "password"})
@EqualsAndHashCode(exclude = "roles")
@Builder
@Entity
public class User {
    @Id
    @Column(unique = true, nullable = false, updatable = false, length = 60)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    private boolean expired;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void addRole(Role role) {
        if(role == null)
            throw new IllegalArgumentException("Role cannot be null...");
        roles.add(role);
    }

    public void removeRole(Role role) {
        if(role == null)
            throw new IllegalArgumentException("Role cannot be null...");
        Predicate<Role> predicate = predicateRole -> roles.contains(predicateRole);
        Consumer<Role> consumer = consumerRole -> roles.remove(consumerRole);
        if(predicate.test(role))
            consumer.accept(role);
        else
            throw new IllegalArgumentException("Role not found...");
    }
}
