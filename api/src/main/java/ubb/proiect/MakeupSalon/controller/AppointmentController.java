package ubb.proiect.MakeupSalon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ubb.proiect.MakeupSalon.converter.AppointmentConverter;
import ubb.proiect.MakeupSalon.dto.AppointmentDto;
import ubb.proiect.MakeupSalon.dto.AppointmentRequestDto;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ubb.proiect.MakeupSalon.service.IAppointmentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AppointmentController {
    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private AppointmentConverter appointmentConverter;


    @Operation(summary = "Find all Appointments",
            description = "Retrieves a comprehensive list of appointments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Appointments found"),
                    @ApiResponse(responseCode = "404", description = "Appointments not found", content = @Content)
            })
    @GetMapping(value = "/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            List<AppointmentDto> appointmentDtos = appointments.stream()
                    .map(appointmentConverter::convertModelToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(appointmentDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Find Appointment by ID",
            description = "Retrieves an appointment by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Appointment found"),
                    @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content)
            })
    @GetMapping(value = "/appointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable
                                                             @Parameter(description = "The id of the appointment")
                                                             int id) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            AppointmentDto appointmentDto = appointmentConverter.convertModelToDto(appointment);
            return ResponseEntity.ok(appointmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new Appointment",
            description = "Creates a new appointment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Appointment created"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
            })
    @PostMapping(value = "/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> addAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {
        try {
            Appointment savedAppointment = appointmentService.saveAppointment(appointmentRequestDto);
            AppointmentDto savedAppointmentDto = appointmentConverter.convertModelToDto(savedAppointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update Appointment by ID",
            description = "Update an appointment details by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Appointment found"),
                    @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content)
            })
    @PutMapping(value = "/appointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable
                                                            @Parameter(description = "The id of the appointment")
                                                            int id,
                                                            @RequestBody AppointmentRequestDto appointmentRequestDto) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentRequestDto);
            AppointmentDto updatedAppointmentDto = appointmentConverter.convertModelToDto(updatedAppointment);
            return ResponseEntity.ok(updatedAppointmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete Appointment by ID",
            description = "Deletes an appointment by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Appointment successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content)
            })
    @DeleteMapping(value="/appointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAppointment(@PathVariable
                                               @Parameter(description = "The id of the appointment")
                                               int id) {
        try {
            appointmentService.deleteAppointmentById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
