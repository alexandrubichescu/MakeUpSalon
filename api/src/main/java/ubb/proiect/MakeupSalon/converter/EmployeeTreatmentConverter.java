package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.EmployeeTreatmentDto;
import ubb.proiect.MakeupSalon.model.Treatment;

@Component
public class EmployeeTreatmentConverter {
    public Treatment convertDtoToModel(EmployeeTreatmentDto treatmentDto) {
        return Treatment.builder()
                .treatmentID(treatmentDto.getTreatmentID())
                .name(treatmentDto.getName())
                .description(treatmentDto.getDescription())
                .estimatedDuration(treatmentDto.getEstimatedDuration())
                .price(treatmentDto.getPrice())
                .pictureURL(treatmentDto.getPictureURL())
                .build();
    }

    public EmployeeTreatmentDto convertModelToDto(Treatment treatment) {
        return EmployeeTreatmentDto.builder()
                .treatmentID(treatment.getTreatmentID())
                .name(treatment.getName())
                .description(treatment.getDescription())
                .estimatedDuration(treatment.getEstimatedDuration())
                .price(treatment.getPrice())
                .pictureURL(treatment.getPictureURL())
                .build();
    }
}
