package se.lexicon.week41_taskmanagement_springrest.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOView;
import se.lexicon.week41_taskmanagement_springrest.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") // Replace with your frontend URL
@RequestMapping("/api/v1/users")
@RestController
@Validated
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
    public ResponseEntity<UserDTOView> doRegisterUser(@RequestBody @Valid UserDTOForm userDTO) {
        UserDTOView responseBody = userService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @GetMapping
    public ResponseEntity<UserDTOView> doGetUserByEmail
    (
        @RequestParam
        @NotNull(message = "Email is required")    //@NotBlank combines @NotNull & @NotEmpty
        @NotEmpty(message = "Email is required")
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")  //@Email
        String email
    ) {
        UserDTOView responseBody = userService.getByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PutMapping("/disable")
    public ResponseEntity<Void> doDisableUserByEmail
    (
         @RequestParam
         @NotBlank(message = "Email is required")
         @Email(message = "Invalid Email format")
         String email
    ) {
        userService.disableByEmail(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/enable")
    public ResponseEntity<Void> doEnableUserByEmail
    (
        @RequestParam
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid Email format")
        String email
    ) {
        userService.enableByEmail(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> doEditUserPasswordByEmail
    (
        @RequestParam
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid Email format")
        String email,

        @RequestParam
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be least 8 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lower case letter" +
                    ", one number and one special character") String password
    ) {
        userService.updatePassword(email, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
