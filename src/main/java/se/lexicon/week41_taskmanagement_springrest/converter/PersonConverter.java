package se.lexicon.week41_taskmanagement_springrest.converter;

import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.PersonDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Person;

public interface PersonConverter {
    Person toPersonEntitySave(PersonDTOFormSave dtoFormSave);

    Person toPersonEntityForm(PersonDTOForm dtoForm);

    PersonDTOFormView toPersonDTOView(Person entity);

    PersonDTOFormView toPersonDTOView(PersonDTOForm dto);

    PersonDTOForm toPersonDTOForm(PersonDTOFormView entity);

    PersonDTOForm toPersonDTOFormEntity(Person entity);
}
