package ubb.proiect.MakeupSalon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TreatmentDto implements Serializable {
    private int treatmentId;
    private String name;
    private String description;
    private int estimatedDuration;
    private double price;
    private String pictureUrl;
    private List<Integer> employeeIds;

    @Override
    public String toString() {
        return "TreatmentDto{" +
                "treatmentID=" + treatmentId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", estimatedDuration=" + estimatedDuration +
                ", price=" + price +
                ", pictureURL='" + pictureUrl + '\'' +
                ", employeeTreatments=" + employeeIds +
                '}';
    }
}
