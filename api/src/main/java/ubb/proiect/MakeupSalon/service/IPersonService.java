package ubb.proiect.MakeupSalon.service;

import org.springframework.web.multipart.MultipartFile;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.Treatment;

import java.util.List;

public interface IPersonService {
    List<Person> getAllPersons();
    Person getPersonById(int id);
    Person savePerson(Person person);
    Person updatePerson(int id, Person person);
    List<Treatment> getTreatmentsByPersonId(int id);
}
