package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.Appointment;
import ubb.proiect.MakeupSalon.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements IAppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;


    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(int id) {
        return appointmentRepository.getReferenceById(id);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }


    @Override
    public void deleteAppointmentById(int id) {
        appointmentRepository.deleteById(id);
    }
}
