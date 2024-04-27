package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.model.User;
import ubb.proiect.MakeupSalon.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public Set<User> getUsersByTreatmentId(int id) {
        Optional<Treatment> treatment = treatmentRepository.findById(id);
        if(treatment.isPresent()){
            return treatment.get().getEmployeeTreatments().stream()
                    .map(EmployeeTreatment::getEmployee)
                    .collect(Collectors.toSet());
        } else {
            throw new ResourceNotFoundException("Treatment not found with id " + id);
        }
    }
}
