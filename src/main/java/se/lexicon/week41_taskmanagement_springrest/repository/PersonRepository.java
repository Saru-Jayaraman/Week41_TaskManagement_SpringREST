package se.lexicon.week41_taskmanagement_springrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    //Find Persons who have no tasks
    @Query(value = "select * from person p where id not in(select t.person_id from person p join task t on p.id = t.person_id and t.person_id is not null)", nativeQuery = true)
    List<Person> findPersonsWithNoTasks();

    //Find Person by User email
    Optional<Person> findByUser_Email(String email);
}
