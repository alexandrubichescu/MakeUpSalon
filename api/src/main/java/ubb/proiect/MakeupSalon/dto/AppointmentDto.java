package ubb.proiect.MakeupSalon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.Status;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AppointmentDto implements Serializable {
    private int appointmentId;
    private int customerId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreated;
    private Status approvalStatus;
    private int employeeId;
    private Integer treatmentId;

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "appointmentId=" + appointmentId +
                ", customerId=" + customerId +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", dateCreated=" + dateCreated +
                ", approvalStatus=" + approvalStatus +
                ", employeeId=" + employeeId +
                ", treatmentsList=" + treatmentId +
                '}';
    }
}
