package ubb.proiect.MakeupSalon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import ubb.proiect.MakeupSalon.exception.DataBaseOperationException;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        log.trace("getAllUsers() --- method entered");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            log.error("getAllUsers() --- users list is empty");
            throw new ResourceNotFoundException("User not found");
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
    public List<User> getUsersByRole(Role role) {
        log.trace("getUsersByRole() --- method entered");

        Role[] roles = Role.values();
        List<Role> rolesList = Arrays.asList(roles);

        if(rolesList.contains(role)) {
            List<User> users = userRepository.findByRole(role);
            if (users.isEmpty()) {
                log.error("getUsersByRole() --- users list is empty");
                throw new ResourceNotFoundException("Users not found");
            }
            log.trace("getUsersByRole(): usersSize={}", users.size());
            return users;
        } else {
            throw new DataBaseOperationException("Role is not supported");
        }
    }

    @Override
    public User saveUser(User user) {
        log.trace("saveUser() --- method entered");
        try {
            User userSaved = userRepository.save(user);
            log.trace("saveUser(): userSaved = {}", userSaved);
            return userSaved;
        } catch (DataIntegrityViolationException e) {
            log.error("Error while saving user: {}", e.getMessage());
            throw new DataBaseOperationException("Error while saving user" + e.getMessage());
        }
    }

    @Override
    public User updateUser(int id, User user) {
        log.trace("updateUser() --- method entered");
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if (optionalUser.isPresent()) {
            User userUpdated = optionalUser.get();
            userUpdated.setFirstName(user.getFirstName());
            userUpdated.setLastName(user.getLastName());
            userUpdated.setPhoneNumber(user.getPhoneNumber());
            userUpdated.setDateOfBirth(user.getDateOfBirth());
            userUpdated.setAddress(user.getAddress());
            userUpdated.setRole(user.getRole());
            userUpdated.setPictureURL(user.getPictureURL());
            userUpdated.setAccountNonExpired(true);
            userUpdated.setAccountNonLocked(true);
            userUpdated.setCredentialsNonExpired(true);
            userUpdated.setEnabled(user.isEnabled());
            log.trace("updateUser(): userUpdated = {}", userUpdated);
            return userRepository.save(userUpdated);
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

    @Override
    public Optional<User> getUserByEmail(String email) {
        log.trace("getUserByEmail() --- method entered");
        return userRepository.findByEmail(email);
    }

    @Override
    public Set<Treatment> getTreatmentsByUserId(int id) {
        log.trace("getTreatmentsByUserId() --- method entered");
        Optional<User> employee = userRepository.findById(id);
        if (employee.isPresent()) {
            Set<EmployeeTreatment> employeeTreatments = employee.get().getEmployeeTreatments();
            Set<Treatment> treatmentsByEmployee = employeeTreatments.stream()
                    .map(EmployeeTreatment::getTreatment)
                    .collect(Collectors.toSet());
            log.trace("getTreatmentsByUserId(): treatmentsSize={}", treatmentsByEmployee.size());
            return treatmentsByEmployee;
        } else {
            log.error("getTreatmentsByUserId(): user not found");
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        }
    }
}
