package se.lexicon.week41_taskmanagement_springrest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.*;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Person;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Task;

@Component
public class TaskConverterImpl implements TaskConverter {

    PersonConverter personConverter;

    @Autowired
    public TaskConverterImpl(@Lazy PersonConverter personConverter) {
        this.personConverter = personConverter;
    }

    @Override
    public Task toTaskSave(TaskDTOFormSave dto) {
        Person personEntity = null;
        if(dto.getPerson() != null)
            personEntity = personConverter.toPersonEntityForm(dto.getPerson());
        return Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .done(dto.isDone())
                .deadLine(dto.getDeadLine())
                .person(personEntity)
                .build();
    }

    @Override
    public Task toTaskForm(TaskDTOForm dto) {
        Person personEntity = null;
        if(dto.getPerson() != null)
            personEntity = personConverter.toPersonEntityForm(dto.getPerson());
        return Task.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .done(dto.isDone())
                .deadLine(dto.getDeadLine())
                .person(personEntity)
                .build();
    }

    @Override
    public TaskDTOFormView toTaskDTOView(Task entity) {
        PersonDTOFormView personDTO = null;
        if(entity.getPerson() != null)
            personDTO = personConverter.toPersonDTOView(entity.getPerson());
        return TaskDTOFormView.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .done(entity.isDone())
                .deadLine(entity.getDeadLine())
                .person(personDTO)
                .build();
    }

    @Override
    public TaskDTOFormView toTaskDTOViewForm(TaskDTOForm dto) {
        PersonDTOFormView personDTO = personConverter.toPersonDTOView(dto.getPerson());
        return TaskDTOFormView.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .done(dto.isDone())
                .deadLine(dto.getDeadLine())
                .person(personDTO)
                .build();
    }

    @Override
    public TaskDTOForm toTaskDTOForm(TaskDTOFormView dto) {
        PersonDTOForm personDTO = null;
        if(dto.getPerson() != null)
            personDTO = personConverter.toPersonDTOForm(dto.getPerson());
        return TaskDTOForm.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .done(dto.isDone())
                .deadLine(dto.getDeadLine())
                .person(personDTO)
                .build();
    }

    @Override
    public TaskDTOForm toTaskDTOFormEntity(Task entity) {
        PersonDTOForm personDTO = null;
        if(entity.getPerson() != null)
            personDTO = personConverter.toPersonDTOFormEntity(entity.getPerson());
        return TaskDTOForm.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .done(entity.isDone())
                .deadLine(entity.getDeadLine())
                .person(personDTO)
                .build();
    }
}
