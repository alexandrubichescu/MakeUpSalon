package ubb.proiect.MakeupSalon.controller;

import ubb.proiect.MakeupSalon.converter.AppointmentConverter;
import ubb.proiect.MakeupSalon.converter.TreatmentConverter;
import ubb.proiect.MakeupSalon.converter.UserConverter;
import ubb.proiect.MakeupSalon.dto.AppointmentDto;
import ubb.proiect.MakeupSalon.dto.IntervalDto;
import ubb.proiect.MakeupSalon.dto.TreatmentDto;
import ubb.proiect.MakeupSalon.dto.UserDto;
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

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private TreatmentConverter treatmentConverter;

    @Autowired
    private AppointmentConverter appointmentConverter;


    public UserController(IUserService userService,
                          UserConverter userConverter,
                          TreatmentConverter treatmentConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.treatmentConverter = treatmentConverter;
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(userConverter::convertModelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/id/{id}")
    public UserDto getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return userConverter.convertModelToDto(user);
    }

    @GetMapping("/users/id/{id}/treatments")
    public Set<TreatmentDto> getTreatmentsByUserId(@PathVariable int id) {
        Set<Treatment> treatments = userService.getTreatmentsByUserId(id);
        return treatments.stream()
                .map(treatmentConverter::convertModelToDto)
                .collect(Collectors.toSet());
    }

    @GetMapping("/users/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        Optional<User> checkUser = userService.getUserByEmail(email);
        if (checkUser.isEmpty()) throw new UserNotFoundException("User with email " + email + " not found");
        return checkUser;
    }

    @GetMapping("/users/role/{role}")
    public List<UserDto> getUsersByRole(@PathVariable Role role) {
        List<User> users = userService.getUsersByRole(role);
        return users.stream()
                .map(userConverter::convertModelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/id/{id}/unavailable")
    public List<IntervalDto> getUnavailableTimes(@PathVariable int id) {
        User employee = userService.getUserById(id);
        Set<Appointment> appointments = employee.getEmployeeAppointments();

        return appointments.stream()
                .map(appointment -> new IntervalDto(appointment.getStartDateTime(), appointment.getEndDateTime()))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/id/{id}/appointments")
    public Set<AppointmentDto> getAppointmentsByUserId(@PathVariable int id) {
        User customer = userService.getUserById(id);
        Set<Appointment> appointments = customer.getEmployeeAppointments();
        return appointments.stream()
                .map(appointmentConverter::convertModelToDto)
                .collect(Collectors.toSet());
    }

    @PostMapping("/users")
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userConverter.convertDtoToModel(userDto);
        user.setRole(Role.CUSTOMER);
        User createdUser = userService.saveUser(user);
        return userConverter.convertModelToDto(createdUser);
    }

    @PutMapping("/users/{id}")
    public UserDto updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        User updatedUser = userService.getUserById(id);

        updatedUser.setFirstName(userDto.getFirstName());
        updatedUser.setLastName(userDto.getLastName());
        updatedUser.setEmail(userDto.getEmail());
        updatedUser.setPassword(userDto.getPassword());
        updatedUser.setPhoneNumber(userDto.getPhoneNumber());
        updatedUser.setDateOfBirth(userDto.getDateOfBirth());
        updatedUser.setAddress(userDto.getAddress());
        updatedUser.setRole(userDto.getRole());
        updatedUser.setPictureURL(userDto.getPictureURL());
        updatedUser.setEmployeeTreatments(userDto.getEmployeeTreatments());
        updatedUser.setCustomerAppointments(userDto.getCustomerAppointments());
        updatedUser.setEmployeeAppointments(userDto.getEmployeeAppointments());
        userService.updateUser(updatedUser);
        return userConverter.convertModelToDto(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
