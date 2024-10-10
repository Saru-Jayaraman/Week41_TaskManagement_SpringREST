package se.lexicon.week41_taskmanagement_springrest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.*;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Person;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Task;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.User;
import se.lexicon.week41_taskmanagement_springrest.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonConverterImpl implements PersonConverter {

    private final TaskRepository taskRepository;
    private final UserConverter userConverter;
    private final TaskConverter taskConverter;

    @Autowired
    public PersonConverterImpl(@Lazy UserConverter userConverter, @Lazy TaskConverter taskConverter, TaskRepository taskRepository) {
        this.userConverter = userConverter;
        this.taskConverter = taskConverter;
        this.taskRepository = taskRepository;
    }

    @Override
    public Person toPersonEntitySave(PersonDTOFormSave dto) {
        User userEntity = userConverter.toUserEntityWithoutRoles(dto.getUser());
        List<Task> taskEntities = new ArrayList<>();
        return Person.builder()
                .name(dto.getName())
                .user(userEntity)
                .taskList(taskEntities)
                .build();
    }

    @Override
    public Person toPersonEntityForm(PersonDTOForm dto) {
        User userEntity = userConverter.toUserEntityWithoutRoles(dto.getUser());
        List<Task> taskEntities = null;
        if (dto.getTaskList() != null)
            taskEntities = dto.getTaskList()
                            .stream()
                            .map(taskConverter::toTaskForm)
                            .toList();
        return Person.builder()
                .id(dto.getId())
                .name(dto.getName())
                .user(userEntity)
                .taskList(taskEntities)
                .build();
    }

    @Override
    public PersonDTOFormView toPersonDTOView(Person entity) {
        UserDTOView userDTO = userConverter.toUserDTOView(entity.getUser());
        List<TaskDTOFormView> taskDTOs = new ArrayList<>();
        List<Task> taskEntities;
        taskEntities = taskRepository.findByPerson_Id(entity.getId());
        for(Task eachTask : taskEntities) {
            taskDTOs.add(taskConverter.toTaskDTOViewWithoutPerson(eachTask));
        }
        return PersonDTOFormView.builder()
                .id(entity.getId())
                .name(entity.getName())
                .user(userDTO)
                .taskList(taskDTOs)
                .build();
    }

    @Override
    public PersonDTOFormView toPersonDTOView(PersonDTOForm dto) {
        UserDTOView userDTO = userConverter.toUserDTOViewForm(dto.getUser());
        return PersonDTOFormView.builder()
                .id(dto.getId())
                .name(dto.getName())
                .user(userDTO)
                .build();
    }

    @Override
    public PersonDTOForm toPersonDTOForm(PersonDTOFormView dto) {
        UserDTOForm userEntity = userConverter.toUserDTOForm(dto.getUser());
        List<TaskDTOForm> taskDTOs = null;
        if (dto.getTaskList() != null)
            taskDTOs = dto.getTaskList()
                .stream()
                .map(taskConverter::toTaskDTOForm)
                .toList();
        return PersonDTOForm.builder()
                .id(dto.getId())
                .name(dto.getName())
                .user(userEntity)
                .taskList(taskDTOs)
                .build();
    }

    @Override
    public PersonDTOForm toPersonDTOFormEntity(Person entity) {
        UserDTOForm userEntity = userConverter.toUserDTOFormEntity(entity.getUser());
        return PersonDTOForm.builder()
                .id(entity.getId())
                .name(entity.getName())
                .user(userEntity)
                .build();
    }
}
