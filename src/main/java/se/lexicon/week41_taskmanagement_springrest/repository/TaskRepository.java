package se.lexicon.week41_taskmanagement_springrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Person;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Task;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    //Select tasks contain title
    List<Task> findByTitleContaining(String title);

    //Select tasks by person's id
    List<Task> findByPerson_Id(Long person_id);

    //Select tasks by status
    List<Task> findByDone(boolean done);

    //Select tasks by date between start and end
    List<Task> findByDeadLineBetween(LocalDate startDate, LocalDate endDate);

    //Select all unassigned tasks
    List<Task> findByPersonIsNull();

    //Select all unfinished tasks
    List<Task> findByDoneFalse();

    //Select all unfinished and overdue tasks
    @Query("select t from Task t where t.done = false and t.deadLine < current_date")
    List<Task> findByDoneFalseAndDeadLineAfter();

    @Modifying
    @Transactional
    @Query("update Task t set t.title = :title, t.description = :description, t.done = :done, t.person = :person where t.id = :id")
    void updateTask(@Param("id") Long id, @Param("title") String title, @Param("description") String description
            , @Param("done") boolean done, @Param("person") Person person);

    boolean existsByPerson_Id(Long personId);
}
