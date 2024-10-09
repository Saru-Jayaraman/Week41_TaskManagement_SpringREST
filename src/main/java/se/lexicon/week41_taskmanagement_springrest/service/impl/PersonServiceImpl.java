package se.lexicon.week41_taskmanagement_springrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.week41_taskmanagement_springrest.converter.PersonConverter;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Person;
import se.lexicon.week41_taskmanagement_springrest.exception.DataNotFoundException;
import se.lexicon.week41_taskmanagement_springrest.repository.PersonRepository;
import se.lexicon.week41_taskmanagement_springrest.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    PersonRepository personRepository;
    PersonConverter personConverter;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PersonConverter personConverter) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
    }

    @Override
    public PersonDTOFormView savePerson(PersonDTOFormSave dtoFormSave) {
        if(dtoFormSave == null)
            throw new IllegalArgumentException("Person Save Form is null/empty...");
        Person personEntity = personConverter.toPersonEntitySave(dtoFormSave);
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
    public PersonDTOFormView update(PersonDTOForm dto) {
        Person person = personRepository.findById(dto.getId())
                .orElseThrow(() -> new DataNotFoundException("Person Id is not valid..."));
        person.setName(dto.getName());
        return personConverter.toPersonDTOView(person);
    }

    @Override
    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Person Id is not valid."));
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
