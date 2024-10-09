package se.lexicon.week41_taskmanagement_springrest.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week41_taskmanagement_springrest.converter.PersonConverter;
import se.lexicon.week41_taskmanagement_springrest.converter.RoleConverter;
import se.lexicon.week41_taskmanagement_springrest.converter.TaskConverter;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.*;
import se.lexicon.week41_taskmanagement_springrest.repository.PersonRepository;
import se.lexicon.week41_taskmanagement_springrest.repository.TaskRepository;
import se.lexicon.week41_taskmanagement_springrest.service.PersonService;
import se.lexicon.week41_taskmanagement_springrest.service.RoleService;
import se.lexicon.week41_taskmanagement_springrest.service.TaskService;
import se.lexicon.week41_taskmanagement_springrest.service.UserService;

import java.time.LocalDate;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    RoleService roleService;
    UserService userService;
    TaskService taskService;
    PersonService personService;
    TaskRepository taskRepository;
    PersonRepository personRepository;
    TaskConverter taskConverter;
    RoleConverter roleConverter;
    PersonConverter personConverter;

    @Autowired
    public DataLoader(RoleService roleService, UserService userService, TaskService taskService, PersonService personService, TaskRepository taskRepository, PersonRepository personRepository, TaskConverter taskConverter, RoleConverter roleConverter, PersonConverter personConverter) {
        this.roleService = roleService;
        this.userService = userService;
        this.taskService = taskService;
        this.personService = personService;
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.taskConverter = taskConverter;
        this.roleConverter = roleConverter;
        this.personConverter = personConverter;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        System.out.println("------------------------------ROLE SERVICE------------------------------");
        System.out.println("----------------------------------SAVE----------------------------------");
        RoleDTOFormSave roleDTOForm1 = new RoleDTOFormSave("ADMIN");
        RoleDTOFormSave roleDTOForm2 = new RoleDTOFormSave("USER");
        RoleDTOFormSave roleDTOForm3 = new RoleDTOFormSave("GUEST");

        RoleDTOFormView roleDTOView1 = roleService.saveRole(roleDTOForm1);
        roleService.saveRole(roleDTOForm2);
        roleService.saveRole(roleDTOForm3);

        System.out.println("--------------------------------FIND ALL--------------------------------");
        List<RoleDTOFormView> allRolesFormView = roleService.getAll();
        allRolesFormView.forEach(System.out::println);

        System.out.println("----------------------------FIND BY ROLE NAME---------------------------");
        RoleDTOFormView findByRoleName = roleService.findByName(roleDTOView1.getName());
        System.out.println(findByRoleName);
        System.out.println();

        List<RoleDTOForm> allRolesForm = new ArrayList<>();
        for(RoleDTOFormView eachDTO : allRolesFormView) {
            allRolesForm.add(roleConverter.toRoleDTOForm(eachDTO));
        }

        System.out.println("------------------------------USER SERVICE------------------------------");
        System.out.println("--------------------------------REGISTER--------------------------------");
        UserDTOForm userDTOForm1 = new UserDTOForm("test1@gmail.com", "test1", Set.of(allRolesForm.get(1), allRolesForm.get(2)));
        UserDTOForm userDTOForm2 = new UserDTOForm("test2@gmail.com", "test2", Set.of(allRolesForm.get(2)));

        UserDTOView userDTOView1 = userService.register(userDTOForm1);
        UserDTOView userDTOView2 = userService.register(userDTOForm2);
        System.out.println(userDTOView1);
        System.out.println(userDTOView2);

        System.out.println("-----------------------------UPDATE PASSWORD----------------------------");
        userService.updatePassword(userDTOForm1.getEmail(), "test11");

        System.out.println("-----------------------------DISABLE EXPIRED----------------------------");
        userService.disableByEmail(userDTOForm1.getEmail());

        System.out.println("-------------------------------GET BY EMAIL-----------------------------");
        UserDTOView getRoleByEmail = userService.getByEmail(userDTOForm1.getEmail());
        System.out.println(getRoleByEmail);

        System.out.println("-----------------------------ENABLE EXPIRED-----------------------------");
        userService.enableByEmail(userDTOForm1.getEmail());
        System.out.println();

        System.out.println("-----------------------------PERSON SERVICE-----------------------------");
        System.out.println("----------------------------------SAVE----------------------------------");
        PersonDTOFormSave person1 = new PersonDTOFormSave("Person1", null, userDTOForm1);
        PersonDTOFormSave person2 = new PersonDTOFormSave("Person2", null, userDTOForm2);

        PersonDTOFormView personDTOView1 = personService.savePerson(person1);
        PersonDTOFormView personDTOView2 = personService.savePerson(person2);
        System.out.println(personDTOView1);
        System.out.println(personDTOView2);

        System.out.println("--------------------------------FIND ALL--------------------------------");
        List<PersonDTOFormView> allPersonsFormView =  personService.getAll();
        allPersonsFormView.forEach(System.out::println);

        System.out.println("-----------------------FIND PERSONS WITH NO TASKS-----------------------");
        List<PersonDTOFormView> allPersonsWithoutTasks =  personService.findPersonsWithNoTasks();
        allPersonsWithoutTasks.forEach(System.out::println);

        System.out.println("-----------------------FIND PERSON WITH USER EMAIL----------------------");
        PersonDTOFormView findPersonWithEmail =  personService.findPersonByUserEmail(userDTOForm1.getEmail());
        System.out.println(findPersonWithEmail);

        System.out.println("-------------------------------FIND BY ID-------------------------------");
        PersonDTOFormView findPersonById = personService.findById(personDTOView1.getId());
        System.out.println(findPersonById);

        System.out.println("---------------------------------UPDATE---------------------------------");
        PersonDTOFormView updatePerson = personService.update(new PersonDTOForm(personDTOView1.getId(), "Person11"));
        System.out.println(updatePerson);

        System.out.println("---------------------------------DELETE---------------------------------");
//        personService.delete(personDTOView2.getId());
//        System.out.println("Person deleted successfully!!!");
        System.out.println();


        System.out.println("------------------------------TASK SERVICE------------------------------");
        System.out.println("----------------------------------SAVE----------------------------------");
        TaskDTOFormSave task1 = new TaskDTOFormSave("Fix light", null, LocalDate.now().plusDays(10), false, null);
//        TaskDTOFormSave task2 = new TaskDTOFormSave("Fix horn", null, LocalDate.now().plusDays(5), false, personConverter.toPersonDTOForm(personDTOView2));
        TaskDTOFormSave task2 = new TaskDTOFormSave("Fix horn", null, LocalDate.now().plusDays(5), false, null);
        TaskDTOFormSave task3 = new TaskDTOFormSave("Check brake", "Sound is produced when applying brake", LocalDate.now().minusDays(1), false, null);

        TaskDTOFormView taskDTOView1 = taskService.saveTask(task1);
        TaskDTOFormView taskDTOView2 = taskService.saveTask(task2);
        TaskDTOFormView taskDTOView3 = taskService.saveTask(task3);
        System.out.println(taskDTOView1);
        System.out.println(taskDTOView2);
        System.out.println(taskDTOView3);

        System.out.println("-------------------------------FIND BY ID-------------------------------");
        TaskDTOFormView findTaskById = taskService.findById(taskDTOView1.getId());
        System.out.println(findTaskById);

        System.out.println("---------------------------------UPDATE---------------------------------");
        taskService.update(new TaskDTOForm(taskDTOView1.getId(),"Fix light", "Brightness problem", LocalDate.now().plusDays(10), false, personConverter.toPersonDTOForm(personDTOView1)));
        System.out.println("Task updated successfully!!!");

        System.out.println("---------------------------------DELETE---------------------------------");
//        taskService.delete(taskDTOView2.getId());
//        System.out.println("Task deleted successfully!!!");

        System.out.println("---------------------------ADD TASK TO PERSON---------------------------");
        List<TaskDTOFormView> addedTask = taskService.addTaskToPerson(personDTOView1.getId(), taskConverter.toTaskDTOForm(taskDTOView3), taskConverter.toTaskDTOForm(taskDTOView2));
        addedTask.forEach(System.out::println);

        System.out.println("-----------------------FIND PERSONS WITH NO TASKS-----------------------");
        allPersonsWithoutTasks =  personService.findPersonsWithNoTasks();
        allPersonsWithoutTasks.forEach(System.out::println);

        System.out.println("-------------------------REMOVE TASK FROM PERSON------------------------");
        taskService.removeTaskFromPerson(personDTOView1.getId(), taskConverter.toTaskDTOForm(taskDTOView3));
        System.out.println("Task removed successfully!!!");

        System.out.println("-----------------------FIND PERSONS WITH NO TASKS-----------------------");
        allPersonsWithoutTasks =  personService.findPersonsWithNoTasks();
        allPersonsWithoutTasks.forEach(System.out::println);

        System.out.println("-------------------------FIND BY TITLE CONTAINS-------------------------");
        String title = "Fix";
        taskService.findByTaskContainTitle(title).forEach(System.out::println);

        System.out.println("---------------------------FIND BY PERSON ID----------------------------");
        taskService.findByPersonId(personDTOView2.getId()).forEach(System.out::println);

        System.out.println("--------------------------FIND BY DONE STATUS---------------------------");
        boolean done = true;
        taskService.findByDone(done).forEach(System.out::println);

        System.out.println("--------------------FIND BY DEADLINE BETWEEN 2 DATES--------------------");
        taskService.findByDeadLineBetween(LocalDate.now().minusDays(10), LocalDate.now().plusDays(20)).forEach(System.out::println);

        System.out.println("-------------------------FIND BY PERSON IS NULL-------------------------");
        taskService.findByPersonIsNull().forEach(System.out::println);

        System.out.println("------------------------FIND BY UNFINISHED TASKS------------------------");
        taskService.findByDoneFalse().forEach(System.out::println);

        System.out.println("-------------------------FIND BY OVERDUE TASKS--------------------------");
        taskService.findByDoneFalseAndDeadLineAfter().forEach(System.out::println);
        System.out.println();
    }
}
