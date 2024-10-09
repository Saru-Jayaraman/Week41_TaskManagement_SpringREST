package se.lexicon.week41_taskmanagement_springrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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
}
