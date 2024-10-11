package se.lexicon.week41_taskmanagement_springrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.*;
import se.lexicon.week41_taskmanagement_springrest.service.TaskService;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/v1/tasks")
@RestController
public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTOFormView> doRegisterTask(@RequestBody TaskDTOFormSave taskDTO) {
        TaskDTOFormView responseBody = taskService.saveTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping
    public ResponseEntity<Void> doEditTask(@RequestBody TaskDTOForm taskDTO) {
        taskService.update(taskDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> doRemoveTask(@RequestParam Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/fetchById")
    public ResponseEntity<TaskDTOFormView> doGetTaskById(@RequestParam Long taskId) {
        TaskDTOFormView responseBody = taskService.findById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchByTitle")
    public ResponseEntity<List<TaskDTOFormView>> doGetByTitle(String title) {
        List<TaskDTOFormView> responseBody = taskService.findByTaskContainTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchByPersonId")
    public ResponseEntity<List<TaskDTOFormView>> doGetTaskByPersonId(@RequestParam Long personId) {
        List<TaskDTOFormView> responseBody = taskService.findByPersonId(personId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchByStatus")
    public ResponseEntity<List<TaskDTOFormView>> doGetTasksByStatus(@RequestParam boolean status) {
        List<TaskDTOFormView> responseBody = taskService.findByDone(status);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchBetween")
    public ResponseEntity<List<TaskDTOFormView>> doGetTaskBetweenDates(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        List<TaskDTOFormView> responseBody = taskService.findByDeadLineBetween(start, end);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchUnassigned")
    public ResponseEntity<List<TaskDTOFormView>> doGetUnassignedTasks() {
        List<TaskDTOFormView> responseBody = taskService.findByPersonIsNull();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchNotDone")
    public ResponseEntity<List<TaskDTOFormView>> doGetNotDoneTasks() {
        List<TaskDTOFormView> responseBody = taskService.findByDoneFalse();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchOverdue")
    public ResponseEntity<List<TaskDTOFormView>> doGetOverdueTasks() {
        List<TaskDTOFormView> responseBody = taskService.findByDoneFalseAndDeadLineAfter();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PutMapping("/addTask")
    public ResponseEntity<List<TaskDTOFormView>> doAddTasks(@RequestParam Long personId, @RequestBody TaskDTOForm... taskDTO) {
        List<TaskDTOFormView> responseBody = taskService.addTaskToPerson(personId, taskDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PutMapping("/removeTask")
    public ResponseEntity<List<TaskDTOFormView>> doRemoveTasks(@RequestParam Long personId, @RequestBody List<TaskDTOForm> taskDTO) {
        taskService.removeTaskFromPerson(personId, taskDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
