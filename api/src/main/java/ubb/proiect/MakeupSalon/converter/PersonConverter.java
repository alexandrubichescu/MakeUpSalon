package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.PersonDto;
import ubb.proiect.MakeupSalon.model.Person;

@Component
public class PersonConverter {
    public Person convertDtoToModel(PersonDto personDto) {
        return Person.builder()
                .personId(personDto.getPersonId())
                .firstName(personDto.getFirstName())
                .lastName(personDto.getLastName())
                .phoneNumber(personDto.getPhoneNumber())
                .dateOfBirth(personDto.getDateOfBirth())
                .address(personDto.getAddress())
                .pictureURL(personDto.getPictureURL())
                .employeeTreatments(personDto.getEmployeeTreatments())
                .customerAppointments(personDto.getCustomerAppointments())
                .employeeAppointments(personDto.getEmployeeAppointments())
                .build();
    }

    public PersonDto convertModelToDto(Person person) {
        return PersonDto.builder()
                .personId(person.getPersonId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .phoneNumber(person.getPhoneNumber())
                .dateOfBirth(person.getDateOfBirth())
                .address(person.getAddress())
                .pictureURL(person.getPictureURL())
                .employeeTreatments(person.getEmployeeTreatments())
                .customerAppointments(person.getCustomerAppointments())
                .employeeAppointments(person.getEmployeeAppointments())
                .build();
    }
}
