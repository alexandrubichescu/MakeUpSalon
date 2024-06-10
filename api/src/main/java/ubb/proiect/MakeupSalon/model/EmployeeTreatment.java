package ubb.proiect.MakeupSalon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private int employeeTreatmentsId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Person employee;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    @JsonIgnore
    private Treatment treatment;
}
