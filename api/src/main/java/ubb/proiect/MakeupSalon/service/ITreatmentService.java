package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.Treatment;

import java.util.List;
import java.util.Set;

public interface ITreatmentService {
    List<Treatment> getAllTreatments();
    Treatment getTreatmentById(int id);
    Treatment saveTreatment(Treatment service);
    Treatment updateTreatment(int id, Treatment treatment);
    void deleteTreatmentById(int id);

    Set<Person> getPersonsByTreatmentId(int id);
}