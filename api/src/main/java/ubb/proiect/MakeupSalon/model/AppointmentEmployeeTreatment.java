package ubb.proiect.MakeupSalon.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "appointment_employee_treatments")
public class AppointmentEmployeeTreatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_employee_treatments_id")
    private int appointmentEmployeeTreatmentsID;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "employee_treatments_id")
    EmployeeTreatment employeeTreatment;
}
