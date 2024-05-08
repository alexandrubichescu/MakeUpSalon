package ubb.proiect.MakeupSalon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ubb.proiect.MakeupSalon.converter.AppointmentConverter;
import ubb.proiect.MakeupSalon.converter.TreatmentConverter;
import ubb.proiect.MakeupSalon.converter.UserConverter;
import ubb.proiect.MakeupSalon.dto.AppointmentDto;
import ubb.proiect.MakeupSalon.dto.IntervalDto;
import ubb.proiect.MakeupSalon.dto.TreatmentDto;
import ubb.proiect.MakeupSalon.dto.UserDto;
import ubb.proiect.MakeupSalon.exception.DataBaseOperationException;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
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


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> usersDto = users.stream()
                    .map(user -> userConverter.convertModelToDto(user))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(usersDto);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        try {
            User user = userService.getUserById(id);
            UserDto userDto = userConverter.convertModelToDto(user);
            return ResponseEntity.ok(userDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/id/{id}/treatments")
    public ResponseEntity<Set<TreatmentDto>> getTreatmentsByUserId(@PathVariable int id) {
        try {
            Set<Treatment> treatments = userService.getTreatmentsByUserId(id);
            Set<TreatmentDto> treatmentDtos = treatments.stream()
                    .map(treatmentConverter::convertModelToDto)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(treatmentDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        Optional<User> checkUser = userService.getUserByEmail(email);
        if (checkUser.isEmpty()) throw new UserNotFoundException("User with email " + email + " not found");
        return checkUser;
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Role role) {
        try {
            List<User> users = userService.getUsersByRole(role);
            List<UserDto> userDtos = users.stream()
                    .map(user -> userConverter.convertModelToDto(user))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataBaseOperationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/users/id/{id}/unavailable")
    public ResponseEntity<List<IntervalDto>> getUnavailableTimes(@PathVariable int id) {
        try {
            User employee = userService.getUserById(id);
            Set<Appointment> appointments = employee.getEmployeeAppointments();
            List<IntervalDto> intervalDtos = appointments.stream()
                    .map(appointment -> new IntervalDto(appointment.getStartDateTime(), appointment.getEndDateTime()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(intervalDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/id/{id}/appointments")
    public ResponseEntity<Set<AppointmentDto>> getAppointmentsByUserId(@PathVariable int id) {
        try {
            User customer = userService.getUserById(id);
            Set<Appointment> appointments = customer.getEmployeeAppointments();
            Set<AppointmentDto> appointmentDtos = appointments.stream()
                    .map(appointmentConverter::convertModelToDto)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(appointmentDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        try {
            User user = userConverter.convertDtoToModel(userDto);
            user.setRole(Role.CUSTOMER); // setting the default role
            User createdUser = userService.saveUser(user);
            UserDto userDtoCreated = userConverter.convertModelToDto(createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoCreated);
        } catch (DataBaseOperationException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        try {
            User updatedUser = userService.updateUser(id, userConverter.convertDtoToModel(userDto));
            UserDto updatedUserDto = userConverter.convertModelToDto(updatedUser);
            return ResponseEntity.ok(updatedUserDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
