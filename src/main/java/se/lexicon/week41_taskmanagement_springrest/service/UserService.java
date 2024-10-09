package se.lexicon.week41_taskmanagement_springrest.service;

import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOView;

public interface UserService {
    UserDTOView register(UserDTOForm userDTOForm);

    void updatePassword(String email, String password);

    UserDTOView getByEmail(String email);

    void disableByEmail(String email);

    void enableByEmail(String email);
}
