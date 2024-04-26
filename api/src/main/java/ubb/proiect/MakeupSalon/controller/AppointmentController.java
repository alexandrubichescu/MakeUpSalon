package ubb.proiect.MakeupSalon.controller;

import ubb.proiect.MakeupSalon.model.Appointment;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.model.Status;
import ubb.proiect.MakeupSalon.model.User;
import ubb.proiect.MakeupSalon.service.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentService;

    @GetMapping("/appointments")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/appointments/{id}")
    public Appointment getAppointmentById(@PathVariable int id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping("/appointments")
    public Appointment addAppointment(@RequestBody Appointment appointment) {
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
            return appointmentService.saveAppointment(appointment);
        }
    }

    @PutMapping("/appointments/{id}")
    public Appointment updateAppointment(@PathVariable int id, @RequestBody Appointment appointment) {
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
            return appointmentService.updateAppointment(appointment);
        }
    }

    @DeleteMapping("/appointments/{id}")
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointmentById(id);
    }
}
