package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.PersonUpdateDto;
import ubb.proiect.MakeupSalon.model.Person;

@Component
public class PersonUpdateConverter {
    public Person convertDtoToModel(PersonUpdateDto personDto) {
        return Person.builder()
                .personId(personDto.getPersonId())
                .firstName(personDto.getFirstName())
                .lastName(personDto.getLastName())
                .phoneNumber(personDto.getPhoneNumber())
                .dateOfBirth(personDto.getDateOfBirth())
                .address(personDto.getAddress())
                .pictureUrl(personDto.getPictureUrl())
                .build();
    }

    public PersonUpdateDto convertModelToDto(Person person) {
        return PersonUpdateDto.builder()
                .personId(person.getPersonId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .phoneNumber(person.getPhoneNumber())
                .dateOfBirth(person.getDateOfBirth())
                .address(person.getAddress())
                .pictureUrl(person.getPictureUrl())
                .build();
    }
}
