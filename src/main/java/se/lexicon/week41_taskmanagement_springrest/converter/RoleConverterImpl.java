package se.lexicon.week41_taskmanagement_springrest.converter;

import org.springframework.stereotype.Component;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Role;

@Component
public class RoleConverterImpl implements RoleConverter {
    @Override
    public Role toRoleEntitySave(RoleDTOFormSave dto) {
        return Role.builder()
                .name(dto.getName())
                .build();
    }

    @Override
    public Role toRoleEntity(RoleDTOForm dto) {
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    @Override
    public RoleDTOFormView toRoleDTOView(Role entity) {
        return RoleDTOFormView.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public RoleDTOFormView toRoleDTOViewForm(RoleDTOForm dto) {
        return RoleDTOFormView.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    @Override
    public RoleDTOForm toRoleDTOForm(RoleDTOFormView dto) {
        return RoleDTOForm.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    @Override
    public RoleDTOForm toRoleDTOFormEntity(Role entity) {
        return RoleDTOForm.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
