package ubb.proiect.MakeupSalon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="persons")
public class Person {
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
    private String pictureURL;

    // Bidirectional One-to-One relationship with User
    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<EmployeeTreatment> employeeTreatments;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Appointment> customerAppointments;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Appointment> employeeAppointments;
}
