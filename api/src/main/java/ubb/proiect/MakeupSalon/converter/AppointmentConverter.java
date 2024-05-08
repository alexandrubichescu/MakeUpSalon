package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.AppointmentDto;
import ubb.proiect.MakeupSalon.model.Appointment;

@Component
public class AppointmentConverter {
    public Appointment convertDtoToModel(AppointmentDto appointmentDto) {
        return Appointment.builder()
                .appointmentID(appointmentDto.getAppointmentID())
                .customer(appointmentDto.getCustomer())
                .startDateTime(appointmentDto.getStartDateTime())
                .endDateTime(appointmentDto.getEndDateTime())
                .dateCreated(appointmentDto.getDateCreated())
                .approvalStatus(appointmentDto.getApprovalStatus())
                .employee(appointmentDto.getEmployee())
                .appointmentEmployeeTreatments(appointmentDto.getAppointmentEmployeeTreatments())
                .build();
    }

    public AppointmentDto convertModelToDto(Appointment appointment) {
        return AppointmentDto.builder()
                .appointmentID(appointment.getAppointmentID())
                .customer(appointment.getCustomer())
                .startDateTime(appointment.getStartDateTime())
                .endDateTime(appointment.getEndDateTime())
                .dateCreated(appointment.getDateCreated())
                .approvalStatus(appointment.getApprovalStatus())
                .employee(appointment.getEmployee())
                .appointmentEmployeeTreatments(appointment.getAppointmentEmployeeTreatments())
                .build();
    }
}
