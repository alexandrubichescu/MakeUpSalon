package ubb.proiect.MakeupSalon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ubb.proiect.MakeupSalon.model.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AppointmentRequestDto implements Serializable {
    private int customerId;
    private LocalDateTime startDateTime;
    private Status approvalStatus = Status.PENDING;
    private int employeeId;
    private int treatmentId;

    @Override
    public String toString() {
        return "AppointmentRequestDto{" +
                "customerId=" + customerId +
                ", startDateTime=" + startDateTime +
                ", employeeId=" + employeeId +
                ", treatmentId=" + treatmentId +
                '}';
    }
}
