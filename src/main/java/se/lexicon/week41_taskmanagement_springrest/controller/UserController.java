package se.lexicon.week41_taskmanagement_springrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOView;
import se.lexicon.week41_taskmanagement_springrest.service.UserService;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
//    Types of GET:
//    1. @RequestParam -> http://localhost:8080/api/v1/users/?email=test1@gmail.com
//    @GetMapping("/")
//    public ResponseEntity<UserDTOView> getByEmail(@RequestParam String email) {

//    2. @PathVariable -> http://localhost:8080/api/v1/users/test1@gmail.com
//    @GetMapping("/{email}")
//    public ResponseEntity<UserDTOView> getByEmail(@PathVariable String email) {
    @PostMapping
    public ResponseEntity<UserDTOView> doRegisterUser(@RequestBody UserDTOForm userDTO) {
        UserDTOView responseBody = userService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @GetMapping
    public ResponseEntity<UserDTOView> doGetUserByEmail(@RequestParam String email) {
        UserDTOView responseBody = userService.getByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PutMapping("/disable")
    public ResponseEntity<Void> doDisableUserByEmail(@RequestParam String email) {
        userService.disableByEmail(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/enable")
    public ResponseEntity<Void> doEnableUserByEmail(@RequestParam String email) {
        userService.enableByEmail(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> doEditUserPasswordByEmail(@RequestParam String email, @RequestParam String password) {
        userService.updatePassword(email, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
