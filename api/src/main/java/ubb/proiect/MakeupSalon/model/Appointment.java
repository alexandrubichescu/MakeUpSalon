package ubb.proiect.MakeupSalon.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private int appointmentID;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name="start_date_time")
    private LocalDateTime startDateTime;

    @Column(name="end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "date_created", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Enumerated(EnumType.STRING)
    @Column(name="approval_status")
    private Status approvalStatus;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User employee;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Set<AppointmentEmployeeTreatment> appointmentEmployeeTreatments;
}
