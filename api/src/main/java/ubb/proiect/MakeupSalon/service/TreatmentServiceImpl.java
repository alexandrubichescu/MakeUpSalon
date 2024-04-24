package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class TreatmentServiceImpl implements ITreatmentService {
    @Autowired
    private TreatmentRepository treatmentRepository;

    @Override
    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    @Override
    public Treatment getTreatmentById(int id) {
        return treatmentRepository.getReferenceById(id);
    }

    @Override
    public Treatment saveTreatment(Treatment service) {
        return treatmentRepository.save(service);
    }

    @Override
    public Treatment updateTreatment(Treatment service) {
        return treatmentRepository.save(service);
    }

    @Override
    public void deleteTreatmentById(int id) {
        treatmentRepository.deleteById(id);
    }
}
