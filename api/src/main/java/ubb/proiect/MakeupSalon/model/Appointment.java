package ubb.proiect.MakeupSalon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "appointments")
@JsonIgnoreProperties("appointmentEmployeeTreatments")
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private int appointmentId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "customer_id",  referencedColumnName = "person_id")
    @JsonIgnore
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

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "approved_by", referencedColumnName = "person_id")
    @JsonIgnore
    private Person employee;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "treatment_id")
    @JsonIgnore
    private Treatment treatment;

    @JsonProperty("customer_id")
    public Integer getCustomerId() {
        return customer != null ? customer.getPersonId() : null;
    }

    @JsonProperty("employee_id")
    public Integer getEmployeeId() {
        return employee != null ? employee.getPersonId() : null;
    }

}
