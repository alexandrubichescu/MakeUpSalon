package ubb.proiect.MakeupSalon.controller;

import ubb.proiect.MakeupSalon.converter.TreatmentConverter;
import ubb.proiect.MakeupSalon.converter.UserConverter;
import ubb.proiect.MakeupSalon.dto.TreatmentDto;
import ubb.proiect.MakeupSalon.dto.UserDto;
import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.model.User;
import ubb.proiect.MakeupSalon.service.ITreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TreatmentController {

    @Autowired
    ITreatmentService treatmentService;
    @Autowired
    private TreatmentConverter treatmentConverter;
    @Autowired
    private UserConverter userConverter;

    @GetMapping("/treatments")
    public List<TreatmentDto> getAllTreatments() {
        List<Treatment> treatments = treatmentService.getAllTreatments();
        return treatments.stream()
                .map(treatmentConverter::convertModelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/treatments/{id}")
    public TreatmentDto getTreatmentById(@PathVariable int id) {
        Treatment treatment = treatmentService.getTreatmentById(id);
        return treatmentConverter.convertModelToDto(treatment);
    }

    @GetMapping("/treatments/{id}/users")
    public Set<UserDto> getUsersByTreatmentId(@PathVariable int id) {
        Set<User> users = treatmentService.getUsersByTreatmentId(id);
        return users.stream()
                .map(userConverter::convertModelToDto)
                .collect(Collectors.toSet());
    }

    @PostMapping("/treatments")
    public TreatmentDto addTreatment(@RequestBody Treatment treatment) {
        treatment.setTreatmentID(0);
        Treatment savedTreatment = treatmentService.saveTreatment(treatment);
        return treatmentConverter.convertModelToDto(savedTreatment);
    }

    @PutMapping("/treatments/{id}")
    public TreatmentDto updateTreatment(@PathVariable int id, @RequestBody Treatment treatment) {
        treatment.setTreatmentID(id);
        Treatment updatedTreatment = treatmentService.updateTreatment(treatment);
        return treatmentConverter.convertModelToDto(updatedTreatment);
    }

    @DeleteMapping("/treatments/{id}")
    public void deleteTreatment(@PathVariable int id) {
        treatmentService.deleteTreatmentById(id);
    }
}
