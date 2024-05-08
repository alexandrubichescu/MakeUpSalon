package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.TreatmentDto;
import ubb.proiect.MakeupSalon.model.Treatment;

@Component
public class TreatmentConverter {
    public Treatment convertDtoToModel(TreatmentDto treatmentDto) {
        return Treatment.builder()
                .treatmentID(treatmentDto.getTreatmentID())
                .name(treatmentDto.getName())
                .description(treatmentDto.getDescription())
                .estimatedDuration(treatmentDto.getEstimatedDuration())
                .price(treatmentDto.getPrice())
                .pictureURL(treatmentDto.getPictureURL())
                .employeeTreatments(treatmentDto.getEmployeeTreatments())
                .build();
    }

    public TreatmentDto convertModelToDto(Treatment treatment) {
        return TreatmentDto.builder()
                .treatmentID(treatment.getTreatmentID())
                .name(treatment.getName())
                .description(treatment.getDescription())
                .estimatedDuration(treatment.getEstimatedDuration())
                .price(treatment.getPrice())
                .pictureURL(treatment.getPictureURL())
                .employeeTreatments(treatment.getEmployeeTreatments())
                .build();
    }
}
