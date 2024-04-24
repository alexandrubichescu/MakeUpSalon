package ubb.proiect.MakeupSalon.controller;

import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.service.ITreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TreatmentController {

    @Autowired
    ITreatmentService treatmentService;

    @GetMapping("/treatments")
    List<Treatment> getTreatments() {
        return treatmentService.getAllTreatments();
    }

    @GetMapping("/treatments/{id}")
    Treatment getTreatmentById(@PathVariable int id) {
        return treatmentService.getTreatmentById(id);
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
