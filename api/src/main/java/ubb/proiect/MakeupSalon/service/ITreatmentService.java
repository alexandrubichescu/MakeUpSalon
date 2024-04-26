package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.model.User;

import java.util.List;
import java.util.Set;

public interface ITreatmentService {
    List<Treatment> getAllTreatments();
    Treatment getTreatmentById(int id);
    Treatment saveTreatment(Treatment service);
    Treatment updateTreatment(Treatment service);
    void deleteTreatmentById(int id);

    Set<User> getUsersByTreatmentId(int id);
}