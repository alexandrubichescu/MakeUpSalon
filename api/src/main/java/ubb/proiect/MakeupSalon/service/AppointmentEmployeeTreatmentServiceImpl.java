package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.AppointmentEmployeeTreatment;
import ubb.proiect.MakeupSalon.repository.AppointmentEmployeeTreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentEmployeeTreatmentServiceImpl implements IAppointmentEmployeeTreatmentService {
    @Autowired
    AppointmentEmployeeTreatmentRepository appointmentEmployeeTreatmentRepository;

    @Override
    public List<AppointmentEmployeeTreatment> getAllAppointmentEmployeeTreatments() {
        return appointmentEmployeeTreatmentRepository.findAll();
    }

    @Override
    public AppointmentEmployeeTreatment getAppointmentEmployeeTreatmentById(int id) {
        return appointmentEmployeeTreatmentRepository.getReferenceById(id);
    }

    @Override
    public AppointmentEmployeeTreatment saveAppointmentEmployeeTreatment(AppointmentEmployeeTreatment appointmentEmployeeTreatment) {
        return appointmentEmployeeTreatmentRepository.save(appointmentEmployeeTreatment);
    }

    @Override
    public AppointmentEmployeeTreatment updateAppointmentEmployeeTreatment(AppointmentEmployeeTreatment appointmentEmployeeTreatment) {
        return appointmentEmployeeTreatmentRepository.save(appointmentEmployeeTreatment);
    }

    @Override
    public void deleteAppointmentEmployeeTreatment(int id) {
        appointmentEmployeeTreatmentRepository.deleteById(id);
    }
}
