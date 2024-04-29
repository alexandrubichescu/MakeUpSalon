package ubb.proiect.MakeupSalon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TreatmentDto implements Serializable {
    private int treatmentID;
    private String name;
    private String description;
    private int estimatedDuration;
    private double price;
    private String pictureURL;
    private Set<EmployeeTreatment> employeeTreatments;

    @Override
    public String toString() {
        return "TreatmentDto{" +
                "treatmentID=" + treatmentID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", estimatedDuration=" + estimatedDuration +
                ", price=" + price +
                ", pictureURL='" + pictureURL + '\'' +
                ", employeeTreatments=" + employeeTreatments +
                '}';
    }
}
