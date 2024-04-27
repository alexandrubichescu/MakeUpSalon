package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.Appointment;

import java.util.List;

public interface IAppointmentService {
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(int id);
    Appointment saveAppointment(Appointment appointment);
    Appointment updateAppointment(Appointment appointment);
    void deleteAppointmentById(int id);
}
