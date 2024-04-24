package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.AppointmentEmployeeTreatment;

import java.util.List;

public interface IAppointmentEmployeeTreatmentService {
    List<AppointmentEmployeeTreatment> getAllAppointmentEmployeeTreatments();
    AppointmentEmployeeTreatment getAppointmentEmployeeTreatmentById(int id);
    AppointmentEmployeeTreatment saveAppointmentEmployeeTreatment(AppointmentEmployeeTreatment appointmentEmployeeTreatment);
    AppointmentEmployeeTreatment updateAppointmentEmployeeTreatment(AppointmentEmployeeTreatment appointmentEmployeeTreatment);
    void deleteAppointmentEmployeeTreatment(int id);
}
