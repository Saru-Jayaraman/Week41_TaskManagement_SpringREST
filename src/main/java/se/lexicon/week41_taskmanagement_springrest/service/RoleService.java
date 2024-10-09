package se.lexicon.week41_taskmanagement_springrest.service;

import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormView;

import java.util.List;

public interface RoleService {
    List<RoleDTOFormView> getAll();

    RoleDTOFormView findByName(String name);

    RoleDTOFormView saveRole(RoleDTOFormSave dtoSaveForm);
}
