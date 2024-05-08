package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.dto.AppointmentRequestDto;
import ubb.proiect.MakeupSalon.model.Appointment;

import java.util.List;

public interface IAppointmentService {
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(int id);
    Appointment saveAppointment(AppointmentRequestDto appointmentRequestDto);
    Appointment updateAppointment(int id, AppointmentRequestDto appointmentRequestDto);
    void deleteAppointmentById(int id);
}
