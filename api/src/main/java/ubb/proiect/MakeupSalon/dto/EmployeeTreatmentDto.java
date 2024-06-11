package ubb.proiect.MakeupSalon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class EmployeeTreatmentDto implements Serializable {
//    @JsonIgnore
    private int employeeTreatmentsId;

    private int treatmentID;
    private int employeeID;
    private String name;
    private String description;
    private int estimatedDuration;
    private double price;
    private String pictureUrl;

    @Override
    public String toString() {
        return "EmployeeTreatmentDto{" +
                "employeeTreatmentsId=" + employeeTreatmentsId +
                "treatmentID=" + treatmentID +
                ", employeeID=" + employeeID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", estimatedDuration=" + estimatedDuration +
                ", price=" + price +
                ", pictureURL='" + pictureUrl + '\'' +
                '}';
    }
}
