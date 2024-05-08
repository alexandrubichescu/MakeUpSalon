package ubb.proiect.MakeupSalon.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "appointment_employee_treatments")
public class AppointmentEmployeeTreatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_employee_treatments_id")
    private int appointmentEmployeeTreatmentsID;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "employee_treatments_id")
    private EmployeeTreatment employeeTreatment;
}
