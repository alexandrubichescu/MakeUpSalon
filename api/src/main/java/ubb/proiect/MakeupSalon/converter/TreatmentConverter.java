package ubb.proiect.MakeupSalon.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.TreatmentDto;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.repository.EmployeeTreatmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TreatmentConverter {

    @Autowired
    private EmployeeTreatmentRepository employeeTreatmentRepository;

    public Treatment convertDtoToModel(TreatmentDto treatmentDto) {
        List<EmployeeTreatment> employeeTreatmentList = treatmentDto.getEmployeeIds().stream()
                .flatMap(id -> employeeTreatmentRepository.findByEmployeePersonId(id).stream())
                .collect(Collectors.toList());

        return Treatment.builder()
                .treatmentId(treatmentDto.getTreatmentId())
                .name(treatmentDto.getName())
                .description(treatmentDto.getDescription())
                .estimatedDuration(treatmentDto.getEstimatedDuration())
                .price(treatmentDto.getPrice())
                .pictureUrl(treatmentDto.getPictureUrl())
                .employeeTreatments(employeeTreatmentList)
                .build();
    }

    public TreatmentDto convertModelToDto(Treatment treatment) {
        List<Integer> employeeIds = treatment.getEmployeeTreatments().stream()
                .map(employeeTreatment -> employeeTreatment.getEmployeeTreatmentsId())
                .collect(Collectors.toList());

        return TreatmentDto.builder()
                .treatmentId(treatment.getTreatmentId())
                .name(treatment.getName())
                .description(treatment.getDescription())
                .estimatedDuration(treatment.getEstimatedDuration())
                .price(treatment.getPrice())
                .pictureUrl(treatment.getPictureUrl())
                .employeeIds(employeeIds)
                .build();
    }
}
