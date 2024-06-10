package ubb.proiect.MakeupSalon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name="treatments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="treatment_id")
    private int treatmentId;

    private String name;
    private String description;

    @Column(name="estimated_duration")
    private int estimatedDuration;
    private double price;

    @Column(name="picture_url")
    private String pictureUrl;

    @OneToMany(mappedBy = "treatment", fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnore
    private List<EmployeeTreatment> employeeTreatments = new ArrayList<>();
}
