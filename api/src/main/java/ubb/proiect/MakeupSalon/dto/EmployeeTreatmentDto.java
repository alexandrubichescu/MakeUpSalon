package ubb.proiect.MakeupSalon.dto;

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
    private int treatmentId;
    private String name;
    private String description;
    private int estimatedDuration;
    private double price;
    private String pictureUrl;

    @Override
    public String toString() {
        return "TreatmentDto{" +
                "treatmentID=" + treatmentId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", estimatedDuration=" + estimatedDuration +
                ", price=" + price +
                ", pictureURL='" + pictureUrl + '\'' +
                '}';
    }
}
