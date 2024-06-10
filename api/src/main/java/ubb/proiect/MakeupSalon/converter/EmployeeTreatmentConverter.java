package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.EmployeeTreatmentDto;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.Treatment;

@Component
public class EmployeeTreatmentConverter {

    public EmployeeTreatment convertDtoToModel(EmployeeTreatmentDto dto) {
        // Assuming Person and Treatment are being handled separately and are set correctly
        EmployeeTreatment employeeTreatment = EmployeeTreatment.builder()
                .employeeTreatmentsId(dto.getEmployeeTreatmentsId())
                .build();

        Person employee = new Person();
        employee.setPersonId(dto.getEmployeeID());
        employeeTreatment.setEmployee(employee);

        Treatment treatment = Treatment.builder()
                .treatmentID(dto.getTreatmentID())
                .name(dto.getName())
                .description(dto.getDescription())
                .estimatedDuration(dto.getEstimatedDuration())
                .price(dto.getPrice())
                .pictureUrl(dto.getPictureUrl())
                .build();
        employeeTreatment.setTreatment(treatment);

        return employeeTreatment;
    }

    public EmployeeTreatmentDto convertModelToDto(EmployeeTreatment model) {
        Treatment treatment = model.getTreatment();
        return EmployeeTreatmentDto.builder()
                .employeeTreatmentsId(model.getEmployeeTreatmentsId())
                .employeeID(model.getEmployee().getPersonId())
                .treatmentID(treatment.getTreatmentID())
                .name(treatment.getName())
                .description(treatment.getDescription())
                .estimatedDuration(treatment.getEstimatedDuration())
                .price(treatment.getPrice())
                .pictureUrl(treatment.getPictureUrl())
                .build();
    }

    // New method to convert Treatment to EmployeeTreatmentDto
    public EmployeeTreatmentDto convertTreatmentToDto(Treatment treatment) {
        return EmployeeTreatmentDto.builder()
                .treatmentID(treatment.getTreatmentID())
                .name(treatment.getName())
                .description(treatment.getDescription())
                .estimatedDuration(treatment.getEstimatedDuration())
                .price(treatment.getPrice())
                .pictureUrl(treatment.getPictureUrl())
                .build();
    }
}
