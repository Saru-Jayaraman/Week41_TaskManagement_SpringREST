package se.lexicon.week41_taskmanagement_springrest.converter;

import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Role;

public interface RoleConverter {
    Role toRoleEntitySave(RoleDTOFormSave dto);

    Role toRoleEntity(RoleDTOForm dto);

    RoleDTOFormView toRoleDTOView(Role entity);

    RoleDTOFormView toRoleDTOViewForm(RoleDTOForm entity);

    RoleDTOForm toRoleDTOForm(RoleDTOFormView viewDTO);

    RoleDTOForm toRoleDTOFormEntity(Role entity);
}
