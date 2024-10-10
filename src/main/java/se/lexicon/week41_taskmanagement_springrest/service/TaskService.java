package se.lexicon.week41_taskmanagement_springrest.service;

import se.lexicon.week41_taskmanagement_springrest.domain.dto.TaskDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.TaskDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.TaskDTOFormView;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    TaskDTOFormView saveTask(TaskDTOFormSave dto);

    TaskDTOFormView findById(Long id);

    void update(TaskDTOForm taskDTOForm);

    void delete(Long id);

    List<TaskDTOFormView> findByTaskContainTitle(String title);

    List<TaskDTOFormView> findByPersonId(Long personId);

    List<TaskDTOFormView> findByDone(boolean done);

    List<TaskDTOFormView> findByDeadLineBetween(LocalDate startDate, LocalDate endDate);

    List<TaskDTOFormView> findByPersonIsNull();

    List<TaskDTOFormView> findByDoneFalse();

    List<TaskDTOFormView> findByDoneFalseAndDeadLineAfter();

    List<TaskDTOFormView> addTaskToPerson(Long personId, TaskDTOForm... taskDTOForm);

    void removeTaskFromPerson(Long personId, List<TaskDTOForm> dto);
}
