package ubb.proiect.MakeupSalon.controller;

import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.model.User;
import ubb.proiect.MakeupSalon.service.ITreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TreatmentController {

    @Autowired
    ITreatmentService treatmentService;

    @GetMapping("/treatments")
    List<Treatment> getAllTreatments() {
        return treatmentService.getAllTreatments();
    }

    @GetMapping("/treatments/{id}")
    Treatment getTreatmentById(@PathVariable int id) {
        return treatmentService.getTreatmentById(id);
    }

    @GetMapping("/treatments/{id}/users")
    Set<User> getUsersByTreatmentId(@PathVariable int id) {
        return treatmentService.getUsersByTreatmentId(id);
    }

    @PostMapping("/treatments")
    Treatment addTreatment(@RequestBody Treatment treatment) {
        treatment.setTreatmentID(0);
        return treatmentService.saveTreatment(treatment);
    }

    @PutMapping("/treatments/{id}")
    Treatment updateTreatment(@PathVariable int id, @RequestBody Treatment treatment) {
        treatment.setTreatmentID(id);
        return treatmentService.updateTreatment(treatment);
    }

    @DeleteMapping("/treatments/{id}")
    void deleteTreatment(@PathVariable int id) {
        treatmentService.deleteTreatmentById(id);
    }
}
