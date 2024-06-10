package ubb.proiect.MakeupSalon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class PersonDto implements Serializable {
    private int personId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String address;
    private String pictureUrl;
    private int userId;
    private List<Integer> treatmentIds;
    private List<Integer> customerAppointmentIds;
    private List<Integer> employeeAppointmentIds;

    @Override
    public String toString() {
        return "PersonDto{" +
                "personId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", userId=" + userId +
                ", treatmentIds=" + treatmentIds +
                ", customerAppointmentIds=" + customerAppointmentIds +
                ", employeeAppointmentIds=" + employeeAppointmentIds +
                '}';
    }
}
