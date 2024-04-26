package ubb.proiect.MakeupSalon.controller;

import ubb.proiect.MakeupSalon.exception.UserNotFoundException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/{id}/treatments")
    public Set<Treatment> getTreatmentsByUserId(@PathVariable int id) {
        return userService.getTreatmentsByUserId(id);
    }

    @GetMapping("/users/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        Optional<User> checkUser = userService.getUserByEmail(email);
        if (checkUser.isEmpty()) throw new UserNotFoundException("User with email " + email + " not found");
        return checkUser;
    }

    @GetMapping("/users/role/{role}")
    public List<User> getUsersByRole(@PathVariable Role role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/users/{id}/unavailable")
    public List<Interval> getUnavaliableTimes(@PathVariable int id) {
        User employee = userService.getUserById(id);
        Set<Appointment> appointments = employee.getEmployeeAppointments();

        return appointments.stream()
                .map(appointment -> new Interval(appointment.getStartDateTime(), appointment.getEndDateTime()))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}/appointments")
    public Set<Appointment> getAppointmentsByUserId(@PathVariable int id) {
        User customer = userService.getUserById(id);
        return customer.getCustomerAppointments();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        user.setRole(Role.CUSTOMER);
        return userService.saveUser(user);
    }

    @PutMapping("/users/{email}")
    public User updateUser(@PathVariable String email, @RequestBody User user) {
        Optional<User> checkUser = userService.getUserByEmail(email);
        if (checkUser.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found");
        } else {
            User updatedUser = checkUser.get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            updatedUser.setDateOfBirth(user.getDateOfBirth());
            updatedUser.setAddress(user.getAddress());
            updatedUser.setRole(user.getRole());
            updatedUser.setPictureURL(user.getPictureURL());
            updatedUser.setEmployeeTreatments(user.getEmployeeTreatments());
            updatedUser.setCustomerAppointments(user.getCustomerAppointments());
            updatedUser.setEmployeeAppointments(user.getEmployeeAppointments());
            userService.updateUser(updatedUser);
            return updatedUser;
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
