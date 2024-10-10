package se.lexicon.week41_taskmanagement_springrest.converter;

import se.lexicon.week41_taskmanagement_springrest.domain.dto.TaskDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.TaskDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.TaskDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Task;

public interface TaskConverter {
    Task toTaskSave(TaskDTOFormSave dto);

    Task toTaskForm(TaskDTOForm dto);

    TaskDTOFormView toTaskDTOView(Task entity);

    TaskDTOFormView toTaskDTOViewWithoutPerson(Task entity);

    TaskDTOFormView toTaskDTOViewForm(TaskDTOForm dto);

    TaskDTOForm toTaskDTOForm(TaskDTOFormView dto);

    TaskDTOForm toTaskDTOForm(Task entity);
}
