package ubb.proiect.MakeupSalon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AppointmentDto implements Serializable {
    private int appointmentID;
    private Person customer;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime dateCreated;
    private Status approvalStatus;
    private Person employee;

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "appointmentID=" + appointmentID +
                ", customer=" + customer +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", dateCreated=" + dateCreated +
                ", approvalStatus=" + approvalStatus +
                ", employee=" + employee +
                '}';
    }
}
