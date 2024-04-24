package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.Treatment;

import java.util.List;

public interface ITreatmentService {
    List<Treatment> getAllTreatments();
    Treatment getTreatmentById(int id);
    Treatment saveTreatment(Treatment service);
    Treatment updateTreatment(Treatment service);
    void deleteTreatmentById(int id);
}
