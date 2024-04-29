package ubb.proiect.MakeupSalon.controller;

import ubb.proiect.MakeupSalon.converter.AppointmentConverter;
import ubb.proiect.MakeupSalon.dto.AppointmentDto;
import ubb.proiect.MakeupSalon.model.Appointment;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.model.Status;
import ubb.proiect.MakeupSalon.model.User;
import ubb.proiect.MakeupSalon.service.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentService;
    @Autowired
    private AppointmentConverter appointmentConverter;

    @GetMapping("/appointments")
    public List<AppointmentDto> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return appointments.stream()
                .map(appointmentConverter::convertModelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/appointments/{id}")
    public AppointmentDto getAppointmentById(@PathVariable int id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return appointmentConverter.convertModelToDto(appointment);
    }

    @PostMapping("/appointments")
    public AppointmentDto addAppointment(@RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = appointmentConverter.convertDtoToModel(appointmentDto);
        appointment.setAppointmentID(0);
        appointment.setApprovalStatus(Status.PENDING);

        // establish the endDateTime
        EmployeeTreatment employeeTreatment =
                appointment.getAppointmentEmployeeTreatments().iterator().next().getEmployeeTreatment();
        LocalDateTime startDateTime = appointment.getStartDateTime();
        int estimatedDuration = employeeTreatment.getTreatment().getEstimatedDuration();
        LocalDateTime endDateTime = startDateTime.plusMinutes(estimatedDuration);
        appointment.setEndDateTime(endDateTime);

        // check if the employee is available for the time interval
        User employee = appointment.getEmployee();
        if(appointmentService
                .hasOverlappingAppointments(employee,appointment.getStartDateTime(),appointment.getEndDateTime())) {
            throw new RuntimeException("The employee is not available during the specified time interval.");
        } else {
            Appointment savedAppointment = appointmentService.saveAppointment(appointment);
            return appointmentConverter.convertModelToDto(savedAppointment);
        }
    }

    @PutMapping("/appointments/{id}")
    public AppointmentDto updateAppointment(@PathVariable int id, @RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        appointment.setAppointmentID(id);

        // establish the endDateTime
        EmployeeTreatment employeeTreatment =
                appointment.getAppointmentEmployeeTreatments().iterator().next().getEmployeeTreatment();
        LocalDateTime startDateTime = appointment.getStartDateTime();
        int estimatedDuration = employeeTreatment.getTreatment().getEstimatedDuration();
        LocalDateTime endDateTime = startDateTime.plusMinutes(estimatedDuration);
        appointment.setEndDateTime(endDateTime);

        // check if the employee is available for the time interval
        User employee = appointment.getEmployee();
        if(appointmentService
                .hasOverlappingAppointments(employee,appointment.getStartDateTime(),appointment.getEndDateTime())) {
            throw new RuntimeException("The employee is not available during the specified time interval.");
        } else {
            Appointment savedAppointment = appointmentService.saveAppointment(appointment);
            return appointmentConverter.convertModelToDto(savedAppointment);
        }
    }

    @DeleteMapping("/appointments/{id}")
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointmentById(id);
    }
}
