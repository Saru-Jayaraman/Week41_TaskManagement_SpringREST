package se.lexicon.week41_taskmanagement_springrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week41_taskmanagement_springrest.converter.PersonConverter;
import se.lexicon.week41_taskmanagement_springrest.converter.TaskConverter;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.TaskDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.TaskDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.TaskDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Person;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Task;
import se.lexicon.week41_taskmanagement_springrest.exception.DataNotFoundException;
import se.lexicon.week41_taskmanagement_springrest.repository.PersonRepository;
import se.lexicon.week41_taskmanagement_springrest.repository.TaskRepository;
import se.lexicon.week41_taskmanagement_springrest.service.TaskService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {

    PersonRepository personRepository;
    TaskRepository taskRepository;
    PersonConverter personConverter;
    TaskConverter taskConverter;

    @Autowired
    public TaskServiceImpl(PersonRepository personRepository, TaskRepository taskRepository, PersonConverter personConverter, TaskConverter taskConverter) {
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
        this.personConverter = personConverter;
        this.taskConverter = taskConverter;
    }

    @Override
    public TaskDTOFormView saveTask(TaskDTOFormSave dto) {
        if(dto == null)
            throw new IllegalArgumentException("Task is null...");
        Task taskEntity = taskConverter.toTaskSave(dto);
        Task savedEntity = taskRepository.save(taskEntity);
        return taskConverter.toTaskDTOView(savedEntity);
    }

    @Override
    public TaskDTOFormView findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Task not found..."));
        return taskConverter.toTaskDTOView(task);
    }

    @Override
    public void update(TaskDTOForm dto) {
        taskRepository.findById(dto.getId())
                .orElseThrow(() -> new DataNotFoundException("Task not found..."));
        Task taskEntity = taskConverter.toTaskForm(dto);
        Person personEntity = personConverter.toPersonEntityForm(dto.getPerson());
        taskRepository.updateTask(taskEntity.getId(), taskEntity.getTitle(), taskEntity.getDescription(),
                                    taskEntity.isDone(), personEntity);
    }

    @Override
    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("Task ID must not be null...");
        if (!taskRepository.existsById(id)) throw new DataNotFoundException("Task not found with id: " + id);
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTOFormView> findByTaskContainTitle(String title) {
        List<Task> taskEntities = taskRepository.findByTitleContaining(title);
        List<TaskDTOFormView> taskDTOViews = new ArrayList<>();
        taskEntities.forEach(eachTask -> taskDTOViews.add(taskConverter.toTaskDTOView(eachTask)));
        return taskDTOViews;
    }

    @Override
    public List<TaskDTOFormView> findByPersonId(Long personId) {
        if(!taskRepository.existsByPerson_Id(personId)) {
            throw new DataNotFoundException("Person does not have any assigned tasks...");
        }
        List<Task> taskEntities = taskRepository.findByPerson_Id(personId);
        List<TaskDTOFormView> taskDTOViews = new ArrayList<>();
        taskEntities.forEach(eachTask -> taskDTOViews.add(taskConverter.toTaskDTOView(eachTask)));
        return taskDTOViews;
    }

    @Override
    public List<TaskDTOFormView> findByDone(boolean done) {
        List<Task> taskEntities = taskRepository.findByDone(done);
        List<TaskDTOFormView> taskDTOViews = new ArrayList<>();
        taskEntities.forEach(eachTask -> taskDTOViews.add(taskConverter.toTaskDTOView(eachTask)));
        return taskDTOViews;
    }

    @Override
    public List<TaskDTOFormView> findByDeadLineBetween(LocalDate startDate, LocalDate endDate) {
        List<Task> taskEntities = taskRepository.findByDeadLineBetween(startDate, endDate);
        List<TaskDTOFormView> taskDTOViews = new ArrayList<>();
        taskEntities.forEach(eachTask -> taskDTOViews.add(taskConverter.toTaskDTOView(eachTask)));
        return taskDTOViews;
    }

    @Override
    public List<TaskDTOFormView> findByPersonIsNull() {
        List<Task> taskEntities = taskRepository.findByPersonIsNull();
        List<TaskDTOFormView> taskDTOViews = new ArrayList<>();
        taskEntities.forEach(eachTask -> taskDTOViews.add(taskConverter.toTaskDTOView(eachTask)));
        return taskDTOViews;
    }

    @Override
    public List<TaskDTOFormView> findByDoneFalse() {
        List<Task> taskEntities = taskRepository.findByDoneFalse();
        List<TaskDTOFormView> taskDTOViews = new ArrayList<>();
        taskEntities.forEach(eachTask -> taskDTOViews.add(taskConverter.toTaskDTOView(eachTask)));
        return taskDTOViews;
    }

    @Override
    public List<TaskDTOFormView> findByDoneFalseAndDeadLineAfter() {
        List<Task> taskEntities = taskRepository.findByDoneFalseAndDeadLineAfter();
        List<TaskDTOFormView> taskDTOViews = new ArrayList<>();
        taskEntities.forEach(eachTask -> taskDTOViews.add(taskConverter.toTaskDTOView(eachTask)));
        return taskDTOViews;
    }

    @Override
    public List<TaskDTOFormView> addTaskToPerson(Long personId, TaskDTOForm... dto) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new DataNotFoundException("Person not found with id: " + personId));
        List<TaskDTOFormView> views = new ArrayList<>();

        for(TaskDTOForm eachTaskForm : dto) {
            taskRepository.findById(eachTaskForm.getId())
                    .orElseThrow(() -> new DataNotFoundException("Create the task before adding the same to the person... " +
                            "Task not found with the id: " + eachTaskForm.getId()));
            Task entity = taskConverter.toTaskForm(eachTaskForm);
            //Adding Task to Person -> Then update
            person.addTask(entity);
            Person savedPerson = personRepository.save(person);

            //Adding Person to Task -> Then update
            eachTaskForm.setPerson(personConverter.toPersonDTOFormEntity(savedPerson));
            Task modifiedEntity = taskConverter.toTaskForm(eachTaskForm);
            taskRepository.save(modifiedEntity);
            views.add(taskConverter.toTaskDTOViewForm(eachTaskForm));
        }
        return views;
    }

    @Override
    @Transactional
    public void removeTaskFromPerson(Long personId, List<TaskDTOForm> dto) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new DataNotFoundException("Person not found with id: " + personId));

        for(TaskDTOForm eachTaskForm : dto) {
            List<Task> tasks = taskRepository.findByPerson_Id(personId);
            if(Objects.requireNonNull(tasks).isEmpty()) {
                throw new DataNotFoundException("No tasks found for the person with id: " + personId);
            }
            Task foundTaskEntity = taskRepository.findById(eachTaskForm.getId())
                    .orElseThrow(() -> new DataNotFoundException("Task not found to remove with the id: " + eachTaskForm.getId()));
            if(!Objects.equals(foundTaskEntity.getPerson().getId(), personId)) {
                throw new DataNotFoundException("Task with id: "+ eachTaskForm.getId() +" was not assigned for the person: " + personId);
            }
            Task entity = taskConverter.toTaskForm(eachTaskForm);
            //Removing Task from Person -> Then update
            person.removeTask(entity);
            personRepository.save(person);

            //Removing Person from Task -> Then update
            eachTaskForm.setPerson(null);
            Task modifiedEntity = taskConverter.toTaskForm(eachTaskForm);
            taskRepository.save(modifiedEntity);
        }
    }
}
