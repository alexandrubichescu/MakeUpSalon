package ubb.proiect.MakeupSalon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ubb.proiect.MakeupSalon.model.Appointment;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.model.Role;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserDto implements Serializable {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private Role role;
    private String pictureURL;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<EmployeeTreatment> employeeTreatments;
    private Set<Appointment> customerAppointments;
    private Set<Appointment> employeeAppointments;

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", pictureURL='" + pictureURL + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", employeeTreatments=" + employeeTreatments +
                ", customerAppointments=" + customerAppointments +
                ", employeeAppointments=" + employeeAppointments +
                '}';
    }
}
