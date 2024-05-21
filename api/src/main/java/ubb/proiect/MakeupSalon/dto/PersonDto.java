package ubb.proiect.MakeupSalon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ubb.proiect.MakeupSalon.model.Appointment;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

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
    private LocalDate dateOfBirth;
    private String address;
    private String pictureURL;
    private PublicUserDto publicUserDto;
    private Set<EmployeeTreatment> employeeTreatments;
    private Set<Appointment> customerAppointments;
    private Set<Appointment> employeeAppointments;

    @Override
    public String toString() {
        return "PersonDto{" +
                "personId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", pictureURL='" + pictureURL + '\'' +
                ", userDto=" + publicUserDto +
                ", employeeTreatments=" + employeeTreatments +
                ", customerAppointments=" + customerAppointments +
                ", employeeAppointments=" + employeeAppointments +
                '}';
    }
}
