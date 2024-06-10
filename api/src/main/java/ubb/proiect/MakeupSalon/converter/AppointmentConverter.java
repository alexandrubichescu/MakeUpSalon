package ubb.proiect.MakeupSalon.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.AppointmentDto;
import ubb.proiect.MakeupSalon.model.Appointment;
import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.service.IPersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentConverter {

    @Autowired
    private IPersonService personService;

    public Appointment convertDtoToModel(AppointmentDto appointmentDto) {
        return Appointment.builder()
                .appointmentId(appointmentDto.getAppointmentId())
                .customer(personService.getPersonById(appointmentDto.getCustomerId()))
                .startDateTime(appointmentDto.getStartDateTime())
                .endDateTime(appointmentDto.getEndDateTime())
                .dateCreated(appointmentDto.getDateCreated())
                .approvalStatus(appointmentDto.getApprovalStatus())
                .employee(personService.getPersonById(appointmentDto.getEmployeeId()))
                .build();
    }

    public AppointmentDto convertModelToDto(Appointment appointment) {

        return AppointmentDto.builder()
                .appointmentId(appointment.getAppointmentId())
                .customerId(appointment.getCustomerId())
                .startDateTime(appointment.getStartDateTime())
                .endDateTime(appointment.getEndDateTime())
                .dateCreated(appointment.getDateCreated())
                .approvalStatus(appointment.getApprovalStatus())
                .employeeId(appointment.getEmployeeId())
                .treatmentId(appointment.getTreatment().getTreatmentId())
                .build();
    }
}
