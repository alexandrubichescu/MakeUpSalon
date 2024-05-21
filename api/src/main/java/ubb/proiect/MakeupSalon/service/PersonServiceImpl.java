package ubb.proiect.MakeupSalon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements IPersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> getAllPersons() {
        log.trace("getAllPersons() --- method entered");
        List<Person> Persons = personRepository.findAll();
        if (Persons.isEmpty()) {
            log.error("getAllPersons() --- Persons list is empty");
            throw new ResourceNotFoundException("Persons not found");
        }
        log.trace("getAllPersons(): PersonsSize={}", Persons.size());
        return Persons;
    }

    @Override
    public Person getPersonById(int id) {
        log.trace("getPersonById() --- method entered");
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person Person = optionalPerson.get();
            log.trace("getPersonById: Person = {}", Person);
            return Person;
        } else {
            log.error("getPersonById: Person not found");
            throw new ResourceNotFoundException("Person with ID = " + id + " not found");
        }
    }

    @Override
    public Person updatePerson(int id, Person Person) {
        log.trace("updatePerson() --- method entered");
        Optional<Person> optionalPerson = personRepository.findById(Person.getPersonId());
        if (optionalPerson.isPresent()) {
            Person personToUpdate = optionalPerson.get();
            personToUpdate.setFirstName(Person.getFirstName());
            personToUpdate.setLastName(Person.getLastName());
            personToUpdate.setPhoneNumber(Person.getPhoneNumber());
            personToUpdate.setDateOfBirth(Person.getDateOfBirth());
            personToUpdate.setAddress(Person.getAddress());
            personToUpdate.setPictureURL(Person.getPictureURL());
            log.trace("updatePerson(): PersonUpdated = {}", personToUpdate);
            return personRepository.save(personToUpdate);
        } else {
            log.error("updatePerson(): Person not found");
            throw new ResourceNotFoundException("Person with ID = " + id + " not found");
        }
    }

    @Override
    public List<Treatment> getTreatmentsByPersonId(int id) {
        log.trace("getTreatmentsByPersonId() --- method entered");
        Optional<Person> employee = personRepository.findById(id);
        if (employee.isPresent()) {
            List<EmployeeTreatment> employeeTreatments = employee.get().getEmployeeTreatments();
            List<Treatment> treatmentsByEmployee = employeeTreatments.stream()
                    .map(EmployeeTreatment::getTreatment)
                    .collect(Collectors.toList());
            log.trace("getTreatmentsByPersonId(): treatmentsSize={}", treatmentsByEmployee.size());
            return treatmentsByEmployee;
        } else {
            log.error("getTreatmentsByPersonId(): Person not found");
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        }
    }
}
