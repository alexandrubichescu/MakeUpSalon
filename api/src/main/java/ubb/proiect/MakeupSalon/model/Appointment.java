package ubb.proiect.MakeupSalon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "appointments")
@JsonIgnoreProperties("appointmentEmployeeTreatments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private int appointmentID;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Person customer;

    @Column(name="start_date_time")
    private LocalDateTime startDateTime;

    @Column(name="end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "date_created", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    @Column(name="approval_status")
    private Status approvalStatus;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Person employee;

    @JsonIgnore
    @OneToMany(mappedBy = "appointment", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AppointmentEmployeeTreatment> appointmentEmployeeTreatments;

}
