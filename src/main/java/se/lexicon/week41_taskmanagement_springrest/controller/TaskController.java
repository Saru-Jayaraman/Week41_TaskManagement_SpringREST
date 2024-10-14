package se.lexicon.week41_taskmanagement_springrest.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.*;
import se.lexicon.week41_taskmanagement_springrest.service.TaskService;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/v1/tasks")
@RestController
@Validated
public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTOFormView> doRegisterTask(@RequestBody @Valid TaskDTOFormSave taskDTO) {
        TaskDTOFormView responseBody = taskService.saveTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping("/editTask")
    public ResponseEntity<Void> doEditTask(@RequestBody @Valid TaskDTOForm taskDTO) {
        taskService.update(taskDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> doRemoveTask(@RequestParam Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/fetchById")
    public ResponseEntity<TaskDTOFormView> doGetTaskById(@RequestParam @PositiveOrZero(message = "Id cannot hold negative value") Long taskId) {
        TaskDTOFormView responseBody = taskService.findById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchByTitle")
    public ResponseEntity<List<TaskDTOFormView>> doGetByTitle(@RequestParam @NotBlank(message = "Title is required") String title) {
        List<TaskDTOFormView> responseBody = taskService.findByTaskContainTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchByPersonId")
    public ResponseEntity<List<TaskDTOFormView>> doGetTaskByPersonId(@RequestParam @PositiveOrZero(message = "Id cannot hold negative value") Long personId) {
        List<TaskDTOFormView> responseBody = taskService.findByPersonId(personId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchByStatus")
    public ResponseEntity<List<TaskDTOFormView>> doGetTasksByStatus(@RequestParam
                                                                    @NotNull(message = "Status is required")
                                                                    @JsonDeserialize(using = NumberDeserializers.BooleanDeserializer.class)
                                                                    Boolean status) {
        List<TaskDTOFormView> responseBody = taskService.findByDone(status);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchBetween")
    public ResponseEntity<List<TaskDTOFormView>> doGetTaskBetweenDates
    (@RequestParam @NotNull(message = "Start date is required") LocalDate start,
     @RequestParam @NotNull(message = "End date is required") LocalDate end) {
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
    public ResponseEntity<List<TaskDTOFormView>> doAddTasks(@RequestParam
                                                            @PositiveOrZero(message = "Id cannot hold negative value")
                                                            Long personId,
                                                            @RequestBody @Valid TaskDTOForm... taskDTO) {
        List<TaskDTOFormView> responseBody = taskService.addTaskToPerson(personId, taskDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PutMapping("/removeTask")
    public ResponseEntity<List<TaskDTOFormView>> doRemoveTasks(@RequestParam
                                                               @PositiveOrZero(message = "Id cannot hold negative value")
                                                               Long personId,
                                                               @RequestBody @Valid List<TaskDTOForm> taskDTO) {
        taskService.removeTaskFromPerson(personId, taskDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
