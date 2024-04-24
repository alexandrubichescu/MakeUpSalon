package ubb.proiect.MakeupSalon.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employee_treatments")
public class EmployeeTreatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_treatments_id")
    private int employeeTreatmentsID;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;
}
