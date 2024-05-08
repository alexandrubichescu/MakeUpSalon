package ubb.proiect.MakeupSalon.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import ubb.proiect.MakeupSalon.dto.AppointmentRequestDto;
import ubb.proiect.MakeupSalon.exception.DataBaseOperationException;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
    private static final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private EmployeeTreatmentRepository employeeTreatmentRepository;

    @Autowired
    private AppointmentEmployeeTreatmentRepository appointmentEmployeeTreatmentRepository;


    @Override
    public List<Appointment> getAllAppointments() {
        log.trace("getAllAppointments() --- method entered");
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            log.trace("getAllAppointments() --- No appointments found");
            throw new ResourceNotFoundException("No Appointments found");
        }
        log.trace("getAllAppointments(): appointments.size={}", appointments.size());
        return appointments;
    }

    @Override
    public Appointment getAppointmentById(int id) {
        log.trace("getAppointmentById(int id) --- method entered");
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            log.trace("getAppointmentById(int id) --- appointment={}", appointment.get());
            return appointment.get();
        } else {
            log.trace("getAppointmentById(int id) --- No appointments found");
            throw new ResourceNotFoundException("No Appointment found");
        }
    }

    private boolean isEmployeeAvailable(User employee, LocalDateTime startDateTime, int treatmentId) {
        log.trace("isEmployeeAvailable(User employee, LocalDateTime startDateTime, int treatmentId) --- method entered");
        int treatmentDuration = treatmentRepository.getReferenceById(treatmentId).getEstimatedDuration();
        LocalDateTime endDateTime = startDateTime.plusMinutes(treatmentDuration);

        List<Appointment> overlappingAppointments = appointmentRepository.findByEmployeeAndStartDateTimeBetween(
                employee, startDateTime, endDateTime);

        log.trace("isEmployeeAvailable() --- overlappingAppointments={}", overlappingAppointments);

        return overlappingAppointments.isEmpty();
    }


    private boolean isAppointmentValidForUpdate(Appointment appointment, AppointmentRequestDto appointmentRequestDto) {
        log.trace("isAppointmentValidForUpdate() --- method entered");
        LocalDateTime requestStartDateTime = appointmentRequestDto.getStartDateTime();
        LocalDateTime requestEndDateTime = requestStartDateTime
                .plusMinutes(treatmentRepository.getReferenceById(appointmentRequestDto.getTreatmentId())
                        .getEstimatedDuration());

        LocalDateTime currentStartDateTime = appointment.getStartDateTime();
        LocalDateTime currentEndDateTime = appointment.getEndDateTime();

        log.trace("isAppointmentValidForUpdate() --- appointment={}", appointment);

        return (requestStartDateTime.isEqual(currentStartDateTime)
                || requestStartDateTime.isAfter(currentEndDateTime))
                && (requestEndDateTime.isEqual(currentEndDateTime)
                || requestEndDateTime.isBefore(currentEndDateTime));
    }

    private void updateAppointmentFields(Appointment appointment, AppointmentRequestDto appointmentRequestDto) {
        log.trace("updateAppointmentFields(Appointment appointmentRequestDto) --- method entered");
        appointment.setCustomer(userRepository.getReferenceById(appointmentRequestDto.getCustomerId()));
        appointment.setStartDateTime(appointmentRequestDto.getStartDateTime());
        appointment.setEmployee(userRepository.getReferenceById(appointmentRequestDto.getEmployeeId()));
        appointment.setApprovalStatus(appointmentRequestDto.getApprovalStatus());
        int treatmentId = appointmentRequestDto.getTreatmentId();
        appointment.setEndDateTime(appointmentRequestDto.getStartDateTime()
                .plusDays(treatmentRepository.getReferenceById(treatmentId)
                        .getEstimatedDuration()));
        log.trace("updateAppointmentFields(appointmentRequestDto) --- appointment={}", appointment);
    }


    @Override
    @Transactional
    public Appointment saveAppointment(AppointmentRequestDto appointmentRequestDto) {
        log.trace("saveAppointment(AppointmentRequestDto appointmentRequestDto) --- method entered");

        User employee = userRepository.getReferenceById(appointmentRequestDto.getEmployeeId());
        LocalDateTime startDateTime = appointmentRequestDto.getStartDateTime();
        int treatmentId = appointmentRequestDto.getTreatmentId();

        if (!isEmployeeAvailable(employee, startDateTime, treatmentId)) {
            log.trace("saveAppointment(AppointmentRequestDto appointmentRequestDto) --- Employee not available at the specified time");
            throw new IllegalArgumentException("Employee not available at the specified time");
        }

        Appointment appointment = createAppointmentFromRequest(appointmentRequestDto);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        AppointmentEmployeeTreatment appointmentEmployeeTreatment
                = saveAppointmentEmployeeTreatment(savedAppointment, treatmentId);
        List<AppointmentEmployeeTreatment> appointmentEmployeeTreatmentsList = new ArrayList<>();
        appointmentEmployeeTreatmentsList.add(appointmentEmployeeTreatment);
        appointment.setAppointmentEmployeeTreatments(appointmentEmployeeTreatmentsList);

        log.trace("saveAppointment(AppointmentRequestDto appointmentRequestDto) --- appointment={}", savedAppointment);

        return savedAppointment;
    }

    private Appointment createAppointmentFromRequest(AppointmentRequestDto appointmentRequestDto) {
        log.trace("createAppointmentFromRequest(AppointmentRequestDto appointmentRequestDto) --- method entered");
        Appointment appointment = new Appointment();
        appointment.setCustomer(userRepository.getReferenceById(appointmentRequestDto.getCustomerId()));
        appointment.setStartDateTime(appointmentRequestDto.getStartDateTime());
        int treatmentId = appointmentRequestDto.getTreatmentId();
        appointment.setEndDateTime(appointmentRequestDto.getStartDateTime()
                .plusDays(treatmentRepository.getReferenceById(treatmentId).getEstimatedDuration()));
        appointment.setDateCreated(LocalDateTime.now());
        appointment.setApprovalStatus(Status.PENDING);
        appointment.setEmployee(userRepository.getReferenceById(appointmentRequestDto.getEmployeeId()));
        return appointment;
    }

    private AppointmentEmployeeTreatment saveAppointmentEmployeeTreatment(Appointment appointment, int treatmentId) {
        log.trace("saveAppointmentEmployeeTreatment() --- appointment={}", appointment);
        List<EmployeeTreatment> employeeTreatments = employeeTreatmentRepository.findByTreatmentTreatmentID(treatmentId);
        if (!employeeTreatments.isEmpty()) {
            AppointmentEmployeeTreatment appointmentEmployeeTreatment = new AppointmentEmployeeTreatment();
            appointmentEmployeeTreatment.setAppointment(appointment);
            appointmentEmployeeTreatment.setEmployeeTreatment(employeeTreatments.get(0));
            return appointmentEmployeeTreatmentRepository.save(appointmentEmployeeTreatment);
        } else {
            throw new DataBaseOperationException("EmployeeTreatment not found");
        }
    }

    @Override
    @Transactional
    public Appointment updateAppointment(int id, AppointmentRequestDto appointmentRequestDto) {
        log.trace("updateAppointment(int id, AppointmentRequestDto appointmentRequestDto) --- method entered");
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty()) {
            log.trace("updateAppointment(int id, AppointmentRequestDto appointmentRequestDto) --- No appointments found");
            throw new ResourceNotFoundException("No Appointment found");
        }

        User employee = userRepository.getReferenceById(appointmentRequestDto.getEmployeeId());
        LocalDateTime startDateTime = appointmentRequestDto.getStartDateTime();
        int treatmentId = appointmentRequestDto.getTreatmentId();

        Appointment appointment = optionalAppointment.get();
        if (!isAppointmentValidForUpdate(appointment, appointmentRequestDto) &&
                !isEmployeeAvailable(employee, startDateTime, treatmentId)) {
            throw new IllegalArgumentException("Invalid appointment update request");
        }

        try {
            updateAppointmentFields(appointment, appointmentRequestDto);
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            log.trace("updateAppointment(int id, AppointmentRequestDto appointmentRequestDto) --- appointment={}", updatedAppointment);
            return updatedAppointment;
        } catch (DataIntegrityViolationException e) {
            log.trace("Error while updating appointment: {}", e.getMessage());
            throw new DataBaseOperationException("Error while updating appointment: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteAppointmentById(int id) {
        log.trace("deleteAppointmentById(int id) --- method entered");
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            appointmentRepository.deleteById(id);
            log.trace("deleteAppointmentById(int id) --- appointment={}", optionalAppointment.get());
        } else {
            log.error("deleteAppointmentById(int id) --- No appointments found");
            throw new ResourceNotFoundException("No Appointment found");
        }
    }
}
