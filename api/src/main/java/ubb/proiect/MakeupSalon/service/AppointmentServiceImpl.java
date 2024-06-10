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
    private PersonRepository personRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;


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
            log.trace("getAppointmentById(int id) --- appointment getDateCreated={}", appointment.get().getDateCreated());
            return appointment.get();
        } else {
            log.trace("getAppointmentById(int id) --- No appointments found");
            throw new ResourceNotFoundException("No Appointment found");
        }
    }

    private boolean isEmployeeNotAvailable(int employeeId, LocalDateTime requestedStartDateTime, int treatmentId) {
        log.trace("isEmployeeNotAvailable(int employeeId, LocalDateTime requestedStartDateTime, int treatmentId) --- method entered");

        int requestedTreatmentDuration = treatmentRepository.getReferenceById(treatmentId).getEstimatedDuration();
        LocalDateTime requestedEndDateTime = requestedStartDateTime.plusMinutes(requestedTreatmentDuration);

        List<Appointment> employeeAppointmentsList = appointmentRepository.findByEmployeeId(employeeId);

        log.trace("employeeAppointmentsList.size={}", employeeAppointmentsList.size());

        if (employeeAppointmentsList.isEmpty()) {
            log.trace("employeeAppointmentsList is empty for employeeId={}", employeeId);
            return false;
        }

        return employeeAppointmentsList.stream()
                .anyMatch(appointment -> {
                    LocalDateTime startDateTime = appointment.getStartDateTime();
                    LocalDateTime endDateTime = appointment.getEndDateTime();
                    return (requestedStartDateTime.isBefore(endDateTime) && requestedEndDateTime.isAfter(startDateTime)) ||
                            (requestedStartDateTime.isEqual(startDateTime) && requestedEndDateTime.isEqual(endDateTime)) ||
                            (requestedStartDateTime.isBefore(startDateTime) && requestedEndDateTime.isAfter(startDateTime)) ||
                            (requestedStartDateTime.isBefore(endDateTime) && requestedEndDateTime.isAfter(endDateTime)) ||
                            (requestedStartDateTime.isAfter(startDateTime) && requestedEndDateTime.isBefore(endDateTime));
                });
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
        appointment.setCustomer(personRepository.getReferenceById(appointmentRequestDto.getCustomerId()));
        appointment.setStartDateTime(appointmentRequestDto.getStartDateTime());
        appointment.setEmployee(personRepository.getReferenceById(appointmentRequestDto.getEmployeeId()));
        appointment.setApprovalStatus(appointmentRequestDto.getApprovalStatus());
        int treatmentId = appointmentRequestDto.getTreatmentId();
        appointment.setEndDateTime(appointmentRequestDto.getStartDateTime()
                .plusMinutes(treatmentRepository.getReferenceById(treatmentId)
                        .getEstimatedDuration()));
        log.trace("updateAppointmentFields(appointmentRequestDto) --- appointment={}", appointment);
    }

    @Override
    @Transactional
    public Appointment saveAppointment(AppointmentRequestDto appointmentRequestDto) {
        log.trace("saveAppointment(AppointmentRequestDto appointmentRequestDto) --- method entered");

        LocalDateTime requestedStartDateTime = appointmentRequestDto.getStartDateTime();
        int treatmentId = appointmentRequestDto.getTreatmentId();
        int employeeId = appointmentRequestDto.getEmployeeId();

        if (isEmployeeNotAvailable(employeeId, requestedStartDateTime, treatmentId)) {
            log.trace("isEmployeeNotAvailable(int employeeId, LocalDateTime requestedStartDateTime, int treatmentId) {}",
                    isEmployeeNotAvailable(employeeId, requestedStartDateTime, treatmentId));
            throw new ResourceNotFoundException("Employee not available at the specified time");
        }

        Appointment appointment = createAppointmentFromRequest(appointmentRequestDto);
        log.trace("createAppointment(int appointmentRequestDto) --- appointment={}", appointment);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.trace("savedAppointment(int appointmentRequestDto) --- savedAppointment={}", savedAppointment);


        Person customer = personRepository.getReferenceById(appointmentRequestDto.getCustomerId());
        customer.getCustomerAppointments().add(savedAppointment);
        log.trace("customer: {}", customer);
        Person employee = personRepository.getReferenceById(appointmentRequestDto.getEmployeeId());
        employee.getEmployeeAppointments().add(savedAppointment);
        log.trace("employee: {}", employee);

        return savedAppointment;
    }

    private Appointment createAppointmentFromRequest(AppointmentRequestDto appointmentRequestDto) {
        log.trace("createAppointmentFromRequest(AppointmentRequestDto appointmentRequestDto) --- method entered");
        Appointment appointment = new Appointment();
        appointment.setCustomer(personRepository.getReferenceById(appointmentRequestDto.getCustomerId()));
        appointment.setStartDateTime(appointmentRequestDto.getStartDateTime());

        int treatmentId = appointmentRequestDto.getTreatmentId();
        Treatment requestedTreatment = treatmentRepository.getReferenceById(treatmentId);
        LocalDateTime endDateTime = appointmentRequestDto.getStartDateTime()
                .plusMinutes(requestedTreatment.getEstimatedDuration());
        appointment.setEndDateTime(endDateTime);

        appointment.setDateCreated(LocalDateTime.now());
        appointment.setApprovalStatus(Status.PENDING);
        appointment.setEmployee(personRepository.getReferenceById(appointmentRequestDto.getEmployeeId()));
        appointment.setTreatment(requestedTreatment);

        log.trace("createAppointment(int appointmentRequestDto) --- createdAppointment={}", appointment);
        log.trace("appointment customerId {}", appointment.getCustomer().getPersonId());

        return appointment;
    }

    @Override
    @Transactional
    public Appointment updateAppointment(int id, AppointmentRequestDto appointmentRequestDto) {
        log.trace("updateAppointment(int id, AppointmentRequestDto appointmentRequestDto) --- method entered");
        log.info("PAYLOAD: {}", appointmentRequestDto);
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty()) {
            log.trace("updateAppointment(int id, AppointmentRequestDto appointmentRequestDto) --- No appointments found");
            throw new ResourceNotFoundException("No Appointment found");
        }

        int employeeId = appointmentRequestDto.getEmployeeId();
        LocalDateTime startDateTime = appointmentRequestDto.getStartDateTime();
        int treatmentId = appointmentRequestDto.getTreatmentId();

        Appointment appointment = optionalAppointment.get();
        if (!isAppointmentValidForUpdate(appointment, appointmentRequestDto) &&
                isEmployeeNotAvailable(employeeId, startDateTime, treatmentId)) {
            throw new ResourceNotFoundException("Invalid appointment update request");
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
