package ubb.proiect.MakeupSalon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ubb.proiect.MakeupSalon.exception.DataBaseOperationException;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonServiceImpl personService;

    @Override
    public List<User> getAllUsers() {
        log.trace("getAllUsers() --- method entered");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            log.error("getAllUsers() --- users list is empty");
            throw new ResourceNotFoundException("Users not found");
        }
        log.trace("getAllUsers(): usersSize={}", users.size());
        return users;
    }

    @Override
    public User getUserById(int id) {
        log.trace("getUserById() --- method entered");
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            log.trace("getUserById: user = {}", user);
            return user;
        } else {
            log.error("getUserById: user not found");
            throw new ResourceNotFoundException("User with ID = " + id + " not found");
        }
    }

    @Override
    public List<User> getUsersByRole(String roleString) {
        log.trace("getUsersByRole() --- method entered");

        Role role;
        try {
            role = Role.valueOf(roleString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DataBaseOperationException("Role is not supported");
        }

        List<User> users = userRepository.findByRole(role);
        if (users.isEmpty()) {
            log.error("getUsersByRole() --- users list is empty");
            throw new ResourceNotFoundException("Users not found");
        }
        log.trace("getUsersByRole(): usersSize={}", users.size());
        return users;
    }

    @Override
    public User updateUser(int id, User user) {
        log.trace("updateUser() --- method entered");
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();
            userToUpdate.setRole(user.getRole());
            userToUpdate.setAccountNonExpired(true);
            userToUpdate.setAccountNonLocked(true);
            userToUpdate.setCredentialsNonExpired(true);
            userToUpdate.setEnabled(user.isEnabled());

            Person personToUpdate = user.getPerson();
            personToUpdate.setFirstName(user.getPerson().getFirstName());
            personToUpdate.setLastName(user.getPerson().getLastName());
            personToUpdate.setPhoneNumber(user.getPerson().getPhoneNumber());
            personToUpdate.setDateOfBirth(user.getPerson().getDateOfBirth());
            personToUpdate.setAddress(user.getPerson().getAddress());
            personToUpdate.setPictureURL(user.getPerson().getPictureURL());
            personService.updatePerson(personToUpdate.getPersonId(), personToUpdate);

            log.trace("updateUser(): userUpdated = {}", userToUpdate);
            return userRepository.save(userToUpdate);
        } else {
            log.error("updateUser(): user not found");
            throw new ResourceNotFoundException("User with ID = " + id + " not found");
        }
    }

    @Override
    public void deleteUserById(int id) {
        log.trace("deleteUserById() --- method entered");
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            log.trace("deleteUserById(): userRemoved = {}", optionalUser.get());
        } else {
            log.error("deleteUserById(): user not found");
            throw new ResourceNotFoundException("User with ID = " + id + " not found");
        }
    }

}
