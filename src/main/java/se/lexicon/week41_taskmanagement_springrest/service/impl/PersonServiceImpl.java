package se.lexicon.week41_taskmanagement_springrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week41_taskmanagement_springrest.converter.PersonConverter;
import se.lexicon.week41_taskmanagement_springrest.converter.TaskConverter;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.TaskDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Person;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Task;
import se.lexicon.week41_taskmanagement_springrest.exception.DataDuplicateException;
import se.lexicon.week41_taskmanagement_springrest.exception.DataNotFoundException;
import se.lexicon.week41_taskmanagement_springrest.repository.PersonRepository;
import se.lexicon.week41_taskmanagement_springrest.repository.TaskRepository;
import se.lexicon.week41_taskmanagement_springrest.repository.UserRepository;
import se.lexicon.week41_taskmanagement_springrest.service.PersonService;
import se.lexicon.week41_taskmanagement_springrest.service.TaskService;
import se.lexicon.week41_taskmanagement_springrest.util.CustomPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final PersonConverter personConverter;
    private final TaskService taskService;
    private final TaskConverter taskConverter;
    private final CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    public PersonServiceImpl(TaskRepository taskRepository, PersonRepository personRepository, UserRepository userRepository, PersonConverter personConverter, TaskService taskService, TaskConverter taskConverter, CustomPasswordEncoder customPasswordEncoder) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.personConverter = personConverter;
        this.taskService = taskService;
        this.taskConverter = taskConverter;
        this.customPasswordEncoder = customPasswordEncoder;
    }

    @Override
    @Transactional
    public PersonDTOFormView savePerson(PersonDTOFormSave dtoFormSave) {
        if(dtoFormSave == null)
            throw new IllegalArgumentException("Person Save Form is null/empty...");
        if(dtoFormSave.getUser() == null)
            throw new IllegalArgumentException("User details is null...");
        if(userRepository.existsByEmail(dtoFormSave.getUser().getEmail()))
            throw new DataDuplicateException("User Already exists...");

        Person personEntity = personConverter.toPersonEntitySave(dtoFormSave);
        String hashedPass = customPasswordEncoder.encode(personEntity.getUser().getPassword());
        personEntity.getUser().setPassword(hashedPass);
        Person savedPerson = personRepository.save(personEntity);
        return personConverter.toPersonDTOView(savedPerson);
    }

    @Override
    public PersonDTOFormView findById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Person Id is not valid..."));
        return personConverter.toPersonDTOView(person);
    }

    @Override
    @Transactional
    public PersonDTOFormView update(PersonDTOForm dto) {
        Person person = personRepository.findById(dto.getId())
                .orElseThrow(() -> new DataNotFoundException("Person Id is not valid..."));
        person.setName(dto.getName());
        personRepository.updatePersonNameById(dto.getId(), dto.getName());
        return personConverter.toPersonDTOView(person);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Person Id is not valid..."));
        List<Task> tasks = taskRepository.findByPerson_Id(id);
        List<TaskDTOForm> taskDTOs = new ArrayList<>();
        for(Task each : tasks) {
            taskDTOs.add(taskConverter.toTaskDTOForm(each));
        }
        person.setTaskList(tasks);
        taskService.removeTaskFromPerson(id, taskDTOs);
        personRepository.delete(person);
    }

    @Override
    public List<PersonDTOFormView> getAll() {
        List<Person> personEntities = personRepository.findAll();
        List<PersonDTOFormView> personDTOViews = new ArrayList<>();
        for(Person eachPerson : personEntities) {
            personDTOViews.add(personConverter.toPersonDTOView(eachPerson));
        }
        return personDTOViews;
    }


    @Override
    public List<PersonDTOFormView> findPersonsWithNoTasks() {
        List<Person> personEntities = personRepository.findPersonsWithNoTasks();
        List<PersonDTOFormView> personDTOViews = new ArrayList<>();
        for(Person eachPerson : personEntities) {
            personDTOViews.add(personConverter.toPersonDTOView(eachPerson));
        }
        return personDTOViews;
    }

    @Override
    public PersonDTOFormView findPersonByUserEmail(String email) {
        if(Objects.requireNonNull(email).trim().isEmpty())
            throw new IllegalArgumentException("Email is either null/empty...");
        Optional<Person> personOptional = personRepository.findByUser_Email(email);
        if(personOptional.isEmpty())
            throw new DataNotFoundException("Person not found... Check the entered email id...");
        return personConverter.toPersonDTOView(personOptional.get());
    }
}
