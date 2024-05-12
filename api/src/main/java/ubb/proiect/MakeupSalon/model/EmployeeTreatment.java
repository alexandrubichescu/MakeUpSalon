package ubb.proiect.MakeupSalon.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "employee_treatments")
public class EmployeeTreatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_treatments_id")
    private int employeeTreatmentsID;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Person employee;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;
}
