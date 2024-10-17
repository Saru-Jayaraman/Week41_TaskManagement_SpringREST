package se.lexicon.week41_taskmanagement_springrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import se.lexicon.week41_taskmanagement_springrest.converter.UserConverter;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOForm;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.UserDTOView;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.Role;
import se.lexicon.week41_taskmanagement_springrest.domain.entity.User;
import se.lexicon.week41_taskmanagement_springrest.exception.DataDuplicateException;
import se.lexicon.week41_taskmanagement_springrest.exception.DataNotFoundException;
import se.lexicon.week41_taskmanagement_springrest.exception.EmailServiceFailedException;
import se.lexicon.week41_taskmanagement_springrest.repository.RoleRepository;
import se.lexicon.week41_taskmanagement_springrest.repository.UserRepository;
import se.lexicon.week41_taskmanagement_springrest.service.EmailService;
import se.lexicon.week41_taskmanagement_springrest.service.UserService;
import se.lexicon.week41_taskmanagement_springrest.util.CustomPasswordEncoder;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    RoleRepository roleRepository;
    UserRepository userRepository;
    UserConverter userConverter;
    CustomPasswordEncoder customPasswordEncoder;
    EmailService emailService;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, UserConverter userConverter, CustomPasswordEncoder customPasswordEncoder, EmailService emailService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.customPasswordEncoder = customPasswordEncoder;
        this.emailService = emailService;
    }

    @Override
    public UserDTOView register(UserDTOForm userDTOForm) {
        //1. Check parameter
        if(userDTOForm == null)
            throw new IllegalArgumentException("User Form cannot be null..");
        //2. Check if email exists in the DB
        if(userRepository.existsByEmail(userDTOForm.getEmail()))
            throw new DataDuplicateException("User Already exists...");
        //3. Validate roles in repository and retrieve them
        Set<Role> roleEntities = userDTOForm.getRoles()
                .stream()
                .map(userDTO -> roleRepository.findById(userDTO.getId())
                        .orElseThrow(() -> new DataNotFoundException("Role is not valid...")))
                .collect(Collectors.toSet());
        //4. Convert UserDTOForm to User entity
        //5. Hash the password
        User userEntity = userConverter.toUserEntity(userDTOForm, roleEntities);
        //6. Save User to the DB
        User savedUser = userRepository.save(userEntity);
        //todo: Send a Welcome Email when registered a new user
        HttpStatusCode statusCode = emailService.sendRegistrationEmail(userDTOForm.getEmail());
        if(!statusCode.is2xxSuccessful()) {
            System.out.println("Status code: " + statusCode);
            throw new EmailServiceFailedException("EMAIL NOT SENT!!!");
        }
        //7. Convert the repository result to UserDTOView
        //8. return the result
        return userConverter.toUserDTOView(savedUser);
    }

    @Override
    public void updatePassword(String email, String password) {
        validateParams(email, "Email");
        validateParams(password, "Password");
        isEmailTaken(email);
        int updatedRows = userRepository.updatePasswordByEmail(email, customPasswordEncoder.encode(password));
        if(updatedRows != 1)
            throw new IllegalArgumentException("Password not updated...");
        System.out.println("Password updated successfully!!!");
    }

    @Override
    public UserDTOView getByEmail(String email) {
        validateParams(email, "Email");
        User user = userRepository.findById(email).orElseThrow(() -> new DataNotFoundException("Email not found..."));
        return userConverter.toUserDTOView(user);
    }

    @Override
    public void disableByEmail(String email) {
        validateParams(email, "Email");
        isEmailTaken(email);
        int updatedRows = userRepository.updateExpiredByEmail(email, true);
        if(updatedRows != 1)
            throw new IllegalArgumentException("Password not updated...");
        System.out.println("Status updated successfully!!!");
    }

    @Override
    public void enableByEmail(String email) {
        validateParams(email, "Email");
        isEmailTaken(email);
        int updatedRows = userRepository.updateExpiredByEmail(email, false);
        if(updatedRows != 1)
            throw new IllegalArgumentException("Password not updated...");
        System.out.println("Status updated successfully!!!");
    }

    private void isEmailTaken(String email) {
        if(!userRepository.existsByEmail(email))
            throw new DataNotFoundException("Email not found...");
    }

    private void validateParams(String param, String paramName) {
        if(Objects.requireNonNull(param).trim().isEmpty())
            throw new IllegalArgumentException(paramName + " is either null/empty...");

    }
}
