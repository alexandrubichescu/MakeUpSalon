package ubb.proiect.MakeupSalon.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.PersonDto;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.User;
import ubb.proiect.MakeupSalon.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonConverter {

    @Autowired
    private UserRepository userRepository;

    public Person convertDtoToModel(PersonDto personDto) {
        User user = null;
        if ((Integer)personDto.getUserId() != null) {
            user = userRepository.findById(personDto.getUserId()).orElse(null);
        }

        return Person.builder()
                .personId(personDto.getPersonId())
                .firstName(personDto.getFirstName())
                .lastName(personDto.getLastName())
                .phoneNumber(personDto.getPhoneNumber())
                .dateOfBirth(personDto.getDateOfBirth())
                .address(personDto.getAddress())
                .pictureUrl(personDto.getPictureUrl())
                .user(user)
                .build();
    }

    public PersonDto convertModelToDto(Person person) {
        Integer userId = null;
        if (person.getUser() != null) {
            userId = person.getUser().getUserId();
        }

        List<Integer> employeeTreatmentIds = person.getEmployeeTreatmentIds();
        List<Integer> customerAppointmentIds = person.getCustomerAppointmentIds();
        List<Integer> employeeAppointmentIds = person.getEmployeeAppointmentIds();

        return PersonDto.builder()
                .personId(person.getPersonId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .phoneNumber(person.getPhoneNumber())
                .dateOfBirth(person.getDateOfBirth())
                .address(person.getAddress())
                .pictureUrl(person.getPictureUrl())
                .userId(userId)
                .treatmentIds(employeeTreatmentIds)
                .customerAppointmentIds(customerAppointmentIds)
                .employeeAppointmentIds(employeeAppointmentIds)
                .build();
    }
}
