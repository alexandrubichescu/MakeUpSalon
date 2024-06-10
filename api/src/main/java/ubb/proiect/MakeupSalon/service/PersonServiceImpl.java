package ubb.proiect.MakeupSalon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ubb.proiect.MakeupSalon.exception.DataBaseOperationException;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.repository.EmployeeTreatmentRepository;
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
        List<Person> persons = personRepository.findAll();
        if (persons.isEmpty()) {
            log.error("getAllPersons() --- persons list is empty");
            throw new ResourceNotFoundException("persons not found");
        }
        log.trace("getAllPersons(): PersonsSize={}", persons.size());
        return persons;
    }

    @Override
    public Person getPersonById(int id) {
        log.trace("getPersonById() --- method entered");
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            log.trace("getPersonById: person = {}", person);
            return person;
        } else {
            log.error("getPersonById: Person not found");
            throw new ResourceNotFoundException("Person with ID = " + id + " not found");
        }
    }

    @Override
    public Person savePerson(Person person) {
        log.trace("savePerson() --- method entered");
        try {
            Person savedPerson = personRepository.save(person);
            log.trace("savedPerson(): savedPerson={}", savedPerson);
            return savedPerson;
        } catch (DataIntegrityViolationException e) {
            log.error("Error while saving treatment: {}", e.getMessage());
            throw new DataBaseOperationException("Error while saving treatment: " + e.getMessage());
        }
    }

    @Override
    public Person updatePerson(int id, Person person) {
        log.trace("updatePerson() --- method entered");
        Optional<Person> optionalPerson = personRepository.findById(id);
        log.trace("---------updatePerson(): person = {}", optionalPerson);
        if (optionalPerson.isPresent()) {
            Person personToUpdate = optionalPerson.get();
            personToUpdate.setFirstName(person.getFirstName());
            personToUpdate.setLastName(person.getLastName());
            personToUpdate.setPhoneNumber(person.getPhoneNumber());
            personToUpdate.setDateOfBirth(person.getDateOfBirth());
            personToUpdate.setAddress(person.getAddress());
            personToUpdate.setPictureUrl(person.getPictureUrl());
            log.trace("updatePerson(): PersonUpdated = {}", personToUpdate);
            return personRepository.save(personToUpdate);
        } else {
            log.error("updatePerson(): person not found");
            throw new ResourceNotFoundException("person with ID = " + id + " not found");
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
