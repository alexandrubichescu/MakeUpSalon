package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.EmployeeTreatmentDto;
import ubb.proiect.MakeupSalon.model.Treatment;

@Component
public class EmployeeTreatmentConverter {
    public Treatment convertDtoToModel(EmployeeTreatmentDto treatmentDto) {
        return Treatment.builder()
                .treatmentId(treatmentDto.getTreatmentId())
                .name(treatmentDto.getName())
                .description(treatmentDto.getDescription())
                .estimatedDuration(treatmentDto.getEstimatedDuration())
                .price(treatmentDto.getPrice())
                .pictureUrl(treatmentDto.getPictureUrl())
                .build();
    }

    public EmployeeTreatmentDto convertModelToDto(Treatment treatment) {
        return EmployeeTreatmentDto.builder()
                .treatmentId(treatment.getTreatmentId())
                .name(treatment.getName())
                .description(treatment.getDescription())
                .estimatedDuration(treatment.getEstimatedDuration())
                .price(treatment.getPrice())
                .pictureUrl(treatment.getPictureUrl())
                .build();
    }
}
