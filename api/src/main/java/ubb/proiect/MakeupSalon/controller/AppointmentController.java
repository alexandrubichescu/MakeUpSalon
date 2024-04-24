package ubb.proiect.MakeupSalon.controller;

import ubb.proiect.MakeupSalon.model.Appointment;
import ubb.proiect.MakeupSalon.service.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return appointmentService.saveAppointment(appointment);
    }

    @PutMapping("/appointments/{id}")
    public Appointment updateAppointment(@PathVariable int id, @RequestBody Appointment appointment) {
        appointment.setAppointmentID(id);
        return appointmentService.updateAppointment(appointment);
    }

    @DeleteMapping("/appointments/{id}")
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointmentById(id);
    }
}
