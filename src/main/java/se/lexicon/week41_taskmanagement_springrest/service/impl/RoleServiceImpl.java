package se.lexicon.week41_taskmanagement_springrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.week41_taskmanagement_springrest.converter.RoleConverter;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Role;
import se.lexicon.week41_taskmanagement_springrest.exception.DataNotFoundException;
import se.lexicon.week41_taskmanagement_springrest.repository.RoleRepository;
import se.lexicon.week41_taskmanagement_springrest.service.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleConverter roleConverter;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    public List<RoleDTOFormView> getAll() {
        List<Role> roleEntities = roleRepository.findAll();
        List<RoleDTOFormView> roleDTOFormViews = new ArrayList<>();
        for(Role eachEntity : roleEntities) {
            roleDTOFormViews.add(roleConverter.toRoleDTOView(eachEntity));
        }
        return roleDTOFormViews;
    }

    @Override
    public RoleDTOFormView findByName(String name) {
        if(Objects.requireNonNull(name).trim().isEmpty())
            throw new IllegalArgumentException("Role name is either null/empty...");
        Optional<Role> roleEntity = roleRepository.findByName(name);
        if(roleEntity.isEmpty())
            throw new DataNotFoundException("Role not found...");
        return roleConverter.toRoleDTOView(roleEntity.get());
    }

    @Override
    public RoleDTOFormView saveRole(RoleDTOFormSave dtoSaveForm) {
        if(dtoSaveForm == null)
            throw new IllegalArgumentException("Role Form for Save operation is either null/empty...");
        Role roleViewForm = roleConverter.toRoleEntitySave(dtoSaveForm);
        Role savedRole = roleRepository.save(roleViewForm);
        return roleConverter.toRoleDTOView(savedRole);
    }
}
