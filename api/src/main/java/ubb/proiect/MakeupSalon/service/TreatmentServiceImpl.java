package ubb.proiect.MakeupSalon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ubb.proiect.MakeupSalon.exception.DataBaseOperationException;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.repository.EmployeeTreatmentRepository;
import ubb.proiect.MakeupSalon.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl implements ITreatmentService {
    private static final Logger log = LoggerFactory.getLogger(TreatmentServiceImpl.class);

    @Autowired
    private TreatmentRepository treatmentRepository;


    @Override
    public List<Treatment> getAllTreatments() {
        log.trace("getAllTreatments() --- method entered");
        List<Treatment> treatments = treatmentRepository.findAll();
        if (treatments.isEmpty()) {
            log.error("getAllTreatments() returned empty list");
            throw new DataBaseOperationException("getAllTreatments() returned empty list");
        } else {
            log.trace("getAllTreatments(): treatments.size={}", treatments.size());
            return treatments;
        }
    }

    @Override
    public Treatment getTreatmentById(int id) {
        log.trace("getTreatmentById() --- method entered");
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(id);
        if (treatmentOptional.isPresent()) {
            Treatment treatment = treatmentOptional.get();
            return treatment;
        } else {
            log.error("getTreatmentById(): treatment={} not found", id);
            throw new ResourceNotFoundException("getTreatmentById(): treatment={} not found");
        }
    }

    @Override
    public Treatment saveTreatment(Treatment treatment) {
        log.trace("saveTreatment() --- method entered");
        try {
            Treatment savedTreatment = treatmentRepository.save(treatment);
            log.trace("saveTreatment(): savedTreatment={}", savedTreatment);
            return savedTreatment;
        } catch (DataIntegrityViolationException e) {
            log.error("Error while saving treatment: {}", e.getMessage());
            throw new DataBaseOperationException("Error while saving treatment: " + e.getMessage());
        }
    }

    @Override
    public Treatment updateTreatment(int id, Treatment treatment) {
        log.trace("updateTreatment() --- method entered");
        Optional<Treatment> optionalTreatment = treatmentRepository.findById(id);
        if (optionalTreatment.isPresent()) {
            Treatment updatedTreatment = optionalTreatment.get();
            updatedTreatment.setName(treatment.getName());
            updatedTreatment.setDescription(treatment.getDescription());
            updatedTreatment.setEstimatedDuration(treatment.getEstimatedDuration());
            updatedTreatment.setPrice(treatment.getPrice());
            updatedTreatment.setPictureUrl(treatment.getPictureUrl());

            log.trace("updateTreatment(): updatedTreatment={}", updatedTreatment);
            return treatmentRepository.save(updatedTreatment);
        } else {
            log.error("updateTreatment(): treatment={} not found", treatment);
            throw new ResourceNotFoundException("Treatment with ID = " + id + " not found");
        }
    }

    @Override
    public void deleteTreatmentById(int id) {
        log.trace("deleteTreatmentById() --- method entered");
        Optional<Treatment> optionalTreatment = treatmentRepository.findById(id);
        if (optionalTreatment.isPresent()) {
            treatmentRepository.delete(optionalTreatment.get());
            log.trace("deleteTreatmentById(): treatment={}", optionalTreatment.get());
        } else {
            log.error("deleteTreatmentById(): treatment={} not found", id);
            throw new ResourceNotFoundException("deleteTreatmentById(): treatment={} not found");
        }
    }

    @Override
    public Set<Person> getPersonsByTreatmentId(int id) {
        Optional<Treatment> treatment = treatmentRepository.findById(id);
        if (treatment.isPresent()) {
            return treatment.get().getEmployeeTreatments().stream()
                    .map(EmployeeTreatment::getEmployee)
                    .collect(Collectors.toSet());
        } else {
            throw new ResourceNotFoundException("Treatment not found with id " + id);
        }
    }
}
