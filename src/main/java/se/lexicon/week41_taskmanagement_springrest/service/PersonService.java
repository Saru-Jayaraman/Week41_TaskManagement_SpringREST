package se.lexicon.week41_taskmanagement_springrest.service;

import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOFormView;

import java.util.List;

public interface PersonService {
    PersonDTOFormView savePerson(PersonDTOFormSave dtoForm);

    PersonDTOFormView findById(Long id);

    PersonDTOFormView update(PersonDTOForm personDTOForm);

    void delete(Long id);

    List<PersonDTOFormView> getAll();

    List<PersonDTOFormView> findPersonsWithNoTasks();

    PersonDTOFormView findPersonByUserEmail(String email);
}
