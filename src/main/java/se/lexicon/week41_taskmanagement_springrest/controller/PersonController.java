package se.lexicon.week41_taskmanagement_springrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.service.PersonService;

import java.util.List;

@RequestMapping("/api/v1/persons")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonDTOFormView> doRegisterPerson(@RequestBody PersonDTOFormSave personDTO) {
        PersonDTOFormView responseBody = personService.savePerson(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping
    public ResponseEntity<PersonDTOFormView> doEditPerson(@RequestBody PersonDTOForm personDTO) {
        PersonDTOFormView responseBody = personService.update(personDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping
    public ResponseEntity<Void> doRemovePerson(@RequestParam Long personId) {
        personService.delete(personId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/fetchById")
    public ResponseEntity<PersonDTOFormView> doGetPersonById(@RequestParam Long personId) {
        PersonDTOFormView responseBody = personService.findById(personId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchByEmail")
    public ResponseEntity<PersonDTOFormView> doGetPersonByEmail(@RequestParam String email) {
        PersonDTOFormView responseBody = personService.findPersonByUserEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<PersonDTOFormView>> doGetAllPersons() {
        List<PersonDTOFormView> responseBody = personService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/fetchIdle")
    public ResponseEntity<List<PersonDTOFormView>> doGetIdlePersons() {
        List<PersonDTOFormView> responseBody = personService.findPersonsWithNoTasks();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
