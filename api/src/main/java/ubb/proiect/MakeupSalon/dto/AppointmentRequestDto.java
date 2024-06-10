package ubb.proiect.MakeupSalon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ubb.proiect.MakeupSalon.model.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AppointmentRequestDto implements Serializable {
    private int customerId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;
    @Builder.Default
    private Status approvalStatus = Status.PENDING;
    private int employeeId;
    private int treatmentId;

    @Override
    public String toString() {
        return "AppointmentRequestDto{" +
                "customerId=" + customerId +
                ", startDateTime=" + startDateTime +
                ", approvalStatus=" + approvalStatus +
                ", employeeId=" + employeeId +
                ", treatmentId=" + treatmentId +
                '}';
    }
}
