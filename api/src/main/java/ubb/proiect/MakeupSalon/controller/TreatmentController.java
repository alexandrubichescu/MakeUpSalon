package ubb.proiect.MakeupSalon.controller;

import org.springframework.http.ResponseEntity;
import ubb.proiect.MakeupSalon.converter.TreatmentConverter;
import ubb.proiect.MakeupSalon.converter.UserConverter;
import ubb.proiect.MakeupSalon.dto.TreatmentDto;
import ubb.proiect.MakeupSalon.dto.UserDto;
import ubb.proiect.MakeupSalon.exception.DataBaseOperationException;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
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
    public ResponseEntity<List<TreatmentDto>> getAllTreatments() {
        try {
            List<Treatment> treatments = treatmentService.getAllTreatments();
            List<TreatmentDto> treatmentDtos = treatments.stream()
                    .map(treatmentConverter::convertModelToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(treatmentDtos);
        } catch (DataBaseOperationException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/treatments/{id}")
    public ResponseEntity<TreatmentDto> getTreatmentById(@PathVariable int id) {
        try {
            Treatment treatment = treatmentService.getTreatmentById(id);
            TreatmentDto treatmentDto = treatmentConverter.convertModelToDto(treatment);
            return ResponseEntity.ok(treatmentDto);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/treatments/{id}/users")
    public ResponseEntity<Set<UserDto>> getUsersByTreatmentId(@PathVariable int id) {
        try {
            Set<User> users = treatmentService.getUsersByTreatmentId(id);
            Set<UserDto> userDtos = users.stream()
                    .map(userConverter::convertModelToDto)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(userDtos);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/treatments")
    public ResponseEntity<TreatmentDto> addTreatment(@RequestBody Treatment treatment) {
        try {
            Treatment savedTreatment = treatmentService.saveTreatment(treatment);
            TreatmentDto savedTreatmentDto = treatmentConverter.convertModelToDto(savedTreatment);
            return ResponseEntity.ok(savedTreatmentDto);
        } catch (DataBaseOperationException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/treatments/{id}")
    public ResponseEntity<TreatmentDto> updateTreatment(@PathVariable int id, @RequestBody Treatment treatment) {
        try {
            Treatment updatedTreatment = treatmentService.updateTreatment(id, treatment);
            TreatmentDto updatedTreatmentDto = treatmentConverter.convertModelToDto(updatedTreatment);
            return ResponseEntity.ok(updatedTreatmentDto);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/treatments/{id}")
    public ResponseEntity<?> deleteTreatment(@PathVariable int id) {
        try {
            treatmentService.deleteTreatmentById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
