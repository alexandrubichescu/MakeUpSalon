package ubb.proiect.MakeupSalon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ubb.proiect.MakeupSalon.converter.PersonConverter;
import ubb.proiect.MakeupSalon.converter.TreatmentConverter;
import ubb.proiect.MakeupSalon.dto.PersonDto;
import ubb.proiect.MakeupSalon.dto.TreatmentDto;
import ubb.proiect.MakeupSalon.exception.DataBaseOperationException;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.Treatment;
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
    private PersonConverter personConverter;


    @Operation(summary = "Find all Treatments",
            description = "Retrieves a comprehensive list of treatments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Treatments found"),
                    @ApiResponse(responseCode = "404", description = "Treatments not found", content = @Content)
            })
    @GetMapping(value = "/treatments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TreatmentDto>> getAllTreatments() {
        try {
            List<Treatment> treatments = treatmentService.getAllTreatments();
            List<TreatmentDto> treatmentDtos = treatments.stream()
                    .map(treatmentConverter::convertModelToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(treatmentDtos);
        } catch (DataBaseOperationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Finds a Treatment by ID",
            description = "Retrieves a single treatment identified by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Treatment found"),
                    @ApiResponse(responseCode = "404", description = "Treatment not found", content = @Content)
            })
    @GetMapping(value = "/treatments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TreatmentDto> getTreatmentById(@PathVariable
                                                         @Parameter(description = "The id of the treatment")
                                                         int id) {
        try {
            Treatment treatment = treatmentService.getTreatmentById(id);
            TreatmentDto treatmentDto = treatmentConverter.convertModelToDto(treatment);
            return ResponseEntity.ok(treatmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Find the Persons(employees) by a Treatment ID",
            description = "Retrieves a set of personss associated with a specific treatment by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Treatment found"),
                    @ApiResponse(responseCode = "404", description = "Treatment not found", content = @Content)
            })
    @GetMapping(value = "/treatments/{id}/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<PersonDto>> getPersonsByTreatmentId(@PathVariable
                                                              @Parameter(description = "The id of the treatment")
                                                              int id) {
        try {
            Set<Person> persons = treatmentService.getPersonsByTreatmentId(id);
            Set<PersonDto> personsDtos = persons.stream()
                    .map(personConverter::convertModelToDto)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(personsDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create new Treatment",
            description = "Adds a new treatment",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Treatment created"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
            })
    @PostMapping(value = "/treatments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TreatmentDto> addTreatment(@RequestBody Treatment treatment) {
        try {
            Treatment savedTreatment = treatmentService.saveTreatment(treatment);
            TreatmentDto savedTreatmentDto = treatmentConverter.convertModelToDto(savedTreatment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTreatmentDto);
        } catch (DataBaseOperationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update Treatment by ID",
            description = "Updates a treatment’s details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Treatment found"),
                    @ApiResponse(responseCode = "404", description = "Treatment not found", content = @Content)
            })
    @PutMapping(value = "/treatments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TreatmentDto> updateTreatment(@PathVariable
                                                        @Parameter(description = "The id of the treatment")
                                                        int id,
                                                        @RequestBody Treatment treatment) {
        try {
            Treatment updatedTreatment = treatmentService.updateTreatment(id, treatment);
            TreatmentDto updatedTreatmentDto = treatmentConverter.convertModelToDto(updatedTreatment);
            return ResponseEntity.ok(updatedTreatmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete Treatment by ID",
            description = "Deletes a treatment identified by its id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Treatment uccessfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Treatment not found", content = @Content)
            })
    @DeleteMapping(value = "/treatments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTreatment(@PathVariable
                                             @Parameter(description = "The id of the treatment")
                                             int id) {
        try {
            treatmentService.deleteTreatmentById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
