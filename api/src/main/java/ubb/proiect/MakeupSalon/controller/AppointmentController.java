package ubb.proiect.MakeupSalon.controller;

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

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private AppointmentConverter appointmentConverter;


    @GetMapping("/appointments")
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

    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable int id) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            AppointmentDto appointmentDto = appointmentConverter.convertModelToDto(appointment);
            return ResponseEntity.ok(appointmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentDto> addAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {
        try {
            Appointment savedAppointment = appointmentService.saveAppointment(appointmentRequestDto);

            AppointmentDto savedAppointmentDto = appointmentConverter.convertModelToDto(savedAppointment);

            return ResponseEntity.ok(savedAppointmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/appointments/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable int id, @RequestBody AppointmentRequestDto appointmentRequestDto) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentRequestDto);

            AppointmentDto updatedAppointmentDto = appointmentConverter.convertModelToDto(updatedAppointment);

            return ResponseEntity.ok(updatedAppointmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable int id) {
        try {
            appointmentService.deleteAppointmentById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
