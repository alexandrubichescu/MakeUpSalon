package ubb.proiect.MakeupSalon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.proiect.MakeupSalon.converter.*;
import ubb.proiect.MakeupSalon.dto.*;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.repository.UserRepository;
import ubb.proiect.MakeupSalon.service.IPersonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private IPersonService personService;

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private AppointmentConverter appointmentConverter;

    @Autowired
    private EmployeeTreatmentConverter employeeTreatmentConverter;

    @Autowired
    private PersonUpdateConverter personUpdateConverter;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Find all Persons",
            description = "Retrieves a comprehensive list of Persons",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Persons found"),
                    @ApiResponse(responseCode = "404", description = "Persons not found", content = @Content)
            })
    @GetMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        try {
            List<Person> persons = personService.getAllPersons();
            List<PersonDto> personDto = persons.stream()
                    .map(person -> personConverter.convertModelToDto(person))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(personDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Find Person by ID",
            description = "Retrieves a Person by their id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Person found"),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
            })
    @GetMapping(value = "/persons/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> getPersonById(@PathVariable
                                                   @Parameter(description = "The id of the Person")
                                                   int id) {
        try {
            Person person = personService.getPersonById(id);
            PersonDto personDto = personConverter.convertModelToDto(person);
            return ResponseEntity.ok(personDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Find the Treatments by a Person(employee) ID",
            description = "Retrieves a set of treatments for a Person specified by their id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Person found"),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
            })
    @GetMapping(value = "/persons/id/{id}/treatments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeTreatmentDto>> getTreatmentsByPersonId(@PathVariable
                                                                              @Parameter(description = "The id of the Person")
                                                                              int id) {
        try {
            List<Treatment> treatments = personService.getTreatmentsByPersonId(id);
            List<EmployeeTreatmentDto> treatmentDtos = treatments.stream()
                    .map(employeeTreatmentConverter::convertModelToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(treatmentDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Find the unavailable dates and times for an employee by Person ID",
            description = "Retrieve a list of time intervals during which a Person (identified by their id) is unavailable",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Person found"),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
            })
    @GetMapping(value = "/persons/id/{id}/unavailable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IntervalDto>> getUnavailableTimes(@PathVariable
                                                                 @Parameter(description = "The id of the Person")
                                                                 int id) {
        try {
            Person employee = personService.getPersonById(id);
            List<Appointment> appointments = employee.getEmployeeAppointments();
            List<IntervalDto> intervalDtos = appointments.stream()
                    .map(appointment -> new IntervalDto(appointment.getStartDateTime(), appointment.getEndDateTime()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(intervalDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Find the appointments by a Person ID",
            description = "Retrieve a set of appointments for a specified Person by their id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Person found"),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
            })
    @GetMapping(value = "/persons/id/{id}/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByPersonId(@PathVariable
                                                                          @Parameter(description = "The id of the Person")
                                                                          int id) {
        try {
            Person person = personService.getPersonById(id);
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Role role = user.getRole();
                if (role == Role.EMPLOYEE) {
                    List<Appointment> appointments = person.getEmployeeAppointments();
                    List<AppointmentDto> appointmentDtos = appointments.stream()
                            .map(appointmentConverter::convertModelToDto)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(appointmentDtos);
                } else if (role == Role.CUSTOMER) {
                    List<Appointment> appointments = person.getCustomerAppointments();
                    List<AppointmentDto> appointmentDtos = appointments.stream()
                            .map(appointmentConverter::convertModelToDto)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(appointmentDtos);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update an existing Person by ID",
            description = "Update an existing person’s information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated"),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
            })
    @PutMapping(value = "/persons/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonUpdateDto> updatePerson(@PathVariable
                                                        @Parameter(description = "The id of the person")
                                                        int id,
                                                        @RequestBody PersonUpdateDto personDto) {
        try {
            Person updatedPerson = personService.updatePerson(id, personUpdateConverter.convertDtoToModel(personDto));
            PersonUpdateDto updatedPersonDto = personUpdateConverter.convertModelToDto(updatedPerson);
            return ResponseEntity.ok(updatedPersonDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
