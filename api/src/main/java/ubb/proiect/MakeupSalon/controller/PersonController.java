package ubb.proiect.MakeupSalon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.proiect.MakeupSalon.converter.AppointmentConverter;
import ubb.proiect.MakeupSalon.converter.PersonConverter;
import ubb.proiect.MakeupSalon.converter.TreatmentConverter;
import ubb.proiect.MakeupSalon.dto.*;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.service.IPersonService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private IPersonService personService;

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private TreatmentConverter treatmentConverter;

    @Autowired
    private AppointmentConverter appointmentConverter;

    @Operation(summary = "Find all Persons",
            description = "Retrieves a comprehensive list of Persons",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Persons found"),
                    @ApiResponse(responseCode = "404", description = "Persons not found", content = @Content)
            })
    @GetMapping(value="/persons", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping(value="/persons/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping(value="/persons/id/{id}/treatments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<TreatmentDto>> getTreatmentsByPersonId(@PathVariable
                                                                   @Parameter(description = "The id of the Person")
                                                                   int id) {
        try {
            Set<Treatment> treatments = personService.getTreatmentsByPersonId(id);
            Set<TreatmentDto> treatmentDtos = treatments.stream()
                    .map(treatmentConverter::convertModelToDto)
                    .collect(Collectors.toSet());
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
            Set<Appointment> appointments = employee.getEmployeeAppointments();
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
    public ResponseEntity<Set<AppointmentDto>> getAppointmentsByPersonId(@PathVariable
                                                                       @Parameter(description = "The id of the Person")
                                                                       int id) {
        try {
            Person customer = personService.getPersonById(id);
            Set<Appointment> appointments = customer.getEmployeeAppointments();
            Set<AppointmentDto> appointmentDtos = appointments.stream()
                    .map(appointmentConverter::convertModelToDto)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(appointmentDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
