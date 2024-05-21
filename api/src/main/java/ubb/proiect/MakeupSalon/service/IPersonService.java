package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.Treatment;

import java.util.List;

public interface IPersonService {
    List<Person> getAllPersons();
    Person getPersonById(int id);
    Person updatePerson(int id, Person person);

    List<Treatment> getTreatmentsByPersonId(int id);
}
