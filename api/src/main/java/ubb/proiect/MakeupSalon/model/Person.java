package ubb.proiect.MakeupSalon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="persons")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="person_id")
    private int personId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    private String address;

    @Column(name="picture_url")
    private String pictureUrl;

    @OneToOne(mappedBy = "person")
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnore
    private List<EmployeeTreatment> employeeTreatments = new ArrayList<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnore
    private List<Appointment> customerAppointments = new ArrayList<>();

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnore
    private List<Appointment> employeeAppointments = new ArrayList<>();

    @JsonProperty("user_id")
    public Integer getUserId() {
        return user != null ? user.getUserId() : null;
    }

    @JsonProperty("employee_treatment_ids")
    public List<Integer> getEmployeeTreatmentIds() {
        return employeeTreatments.stream()
                .map(EmployeeTreatment::getEmployeeTreatmentsId)
                .collect(Collectors.toList());
    }

    @JsonProperty("customer_appointment_ids")
    public List<Integer> getCustomerAppointmentIds() {
        return customerAppointments.stream()
                .map(Appointment::getAppointmentId)
                .collect(Collectors.toList());
    }

    @JsonProperty("employee_appointment_ids")
    public List<Integer> getEmployeeAppointmentIds() {
        return employeeAppointments.stream()
                .map(Appointment::getAppointmentId)
                .collect(Collectors.toList());
    }
}
