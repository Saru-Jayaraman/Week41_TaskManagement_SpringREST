package se.lexicon.week41_taskmanagement_springrest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOView;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Role;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.User;
import se.lexicon.week41_taskmanagement_springrest.util.CustomPasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverterImpl implements UserConverter {

    RoleConverter roleConverter;
    CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    public UserConverterImpl(RoleConverter roleConverter, CustomPasswordEncoder customPasswordEncoder) {
        this.roleConverter = roleConverter;
        this.customPasswordEncoder = customPasswordEncoder;
    }

    @Override
    public User toUserEntity(UserDTOForm dto, Set<Role> roleEntities) {
        return User.builder()
                .email(dto.getEmail())
                .password(customPasswordEncoder.encode(dto.getPassword()))
                .roles(roleEntities)
                .build();
    }

    @Override
    public User toUserEntityWithoutRoles(UserDTOForm dto) {
        Set<Role> roleEntities = dto.getRoles()
                .stream()
                .map(role -> roleConverter.toRoleEntity(role))
                .collect(Collectors.toSet());
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .roles(roleEntities)
                .build();
    }

    @Override
    public UserDTOView toUserDTOView(User entity) {
        Set<RoleDTOFormView> roleDTOs = entity.getRoles()
                .stream()
                .map(role -> roleConverter.toRoleDTOView(role))
                .collect(Collectors.toSet());
        return UserDTOView.builder()
                .email(entity.getEmail())
                .roles(roleDTOs)
                .build();
    }

    @Override
    public UserDTOView toUserDTOViewForm(UserDTOForm dto) {
        Set<RoleDTOFormView> roleDTOs = dto.getRoles()
                .stream()
                .map(role -> roleConverter.toRoleDTOViewForm(role))
                .collect(Collectors.toSet());
        return UserDTOView.builder()
                .email(dto.getEmail())
                .roles(roleDTOs)
                .build();
    }

    @Override
    public UserDTOForm toUserDTOForm(UserDTOView dto) {
        Set<RoleDTOForm> roleDTOs = dto.getRoles()
                .stream()
                .map(role -> roleConverter.toRoleDTOForm(role))
                .collect(Collectors.toSet());
        return UserDTOForm.builder()
                .email(dto.getEmail())
                .roles(roleDTOs)
                .build();
    }

    @Override
    public UserDTOForm toUserDTOFormEntity(User entity) {
        Set<RoleDTOForm> roleDTOs = entity.getRoles()
                .stream()
                .map(role -> roleConverter.toRoleDTOFormEntity(role))
                .collect(Collectors.toSet());
        return UserDTOForm.builder()
                .email(entity.getEmail())
                .roles(roleDTOs)
                .build();
    }
}
