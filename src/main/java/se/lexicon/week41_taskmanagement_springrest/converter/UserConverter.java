package se.lexicon.week41_taskmanagement_springrest.converter;

import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOView;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Role;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.User;

import java.util.Set;

public interface UserConverter {
    User toUserEntity(UserDTOForm dto, Set<Role> roleEntities);

    User toUserEntityWithoutRoles(UserDTOForm dto);

    UserDTOView toUserDTOView(User entity);

    UserDTOView toUserDTOViewForm(UserDTOForm dto);

    UserDTOForm toUserDTOForm(UserDTOView dto);

    UserDTOForm toUserDTOFormEntity(User entity);
}
