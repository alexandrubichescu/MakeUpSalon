package ubb.proiect.MakeupSalon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ubb.proiect.MakeupSalon.converter.EmployeeTreatmentConverter;
import ubb.proiect.MakeupSalon.dto.EmployeeTreatmentDto;
import ubb.proiect.MakeupSalon.exception.DataBaseOperationException;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.service.IEmployeeTreatmentService;
import ubb.proiect.MakeupSalon.service.IPersonService;
import ubb.proiect.MakeupSalon.service.ITreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EmployeeTreatmentController {

    @Autowired
    IEmployeeTreatmentService employeeTreatmentService;

    @Autowired
    private EmployeeTreatmentConverter employeeTreatmentConverter;

    @Autowired
    private IPersonService personService;

    @Autowired
    private ITreatmentService treatmentService;

    @Operation(summary = "Find all Employee Treatments",
            description = "Retrieves a comprehensive list of employee treatments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee Treatments found"),
                    @ApiResponse(responseCode = "404", description = "Employee Treatments not found", content = @Content)
            })
    @GetMapping(value = "/employee-treatments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeTreatmentDto>> getAllEmployeeTreatments() {
        try {
            List<EmployeeTreatment> employeeTreatments = employeeTreatmentService.getAllEmployeeTreatments();
            List<EmployeeTreatmentDto> employeeTreatmentDtos = employeeTreatments.stream()
                    .map(employeeTreatmentConverter::convertModelToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(employeeTreatmentDtos);
        } catch (DataBaseOperationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Finds an Employee Treatment by ID",
            description = "Retrieves a single employee treatment identified by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee Treatment found"),
                    @ApiResponse(responseCode = "404", description = "Employee Treatment not found", content = @Content)
            })
    @GetMapping(value = "/employee-treatments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeTreatmentDto> getEmployeeTreatmentById(@PathVariable
                                                                         @Parameter(description = "The id of the employee treatment")
                                                                         int id) {
        try {
            EmployeeTreatment employeeTreatment = employeeTreatmentService.getEmployeeTreatmentById(id);
            EmployeeTreatmentDto employeeTreatmentDto = employeeTreatmentConverter.convertModelToDto(employeeTreatment);
            return ResponseEntity.ok(employeeTreatmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create new Employee Treatment",
            description = "Adds a new employee treatment",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee Treatment created"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
            })
    @PostMapping(value = "/employee-treatments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeTreatmentDto> addEmployeeTreatment(@RequestBody EmployeeTreatmentDto employeeTreatmentDto) {
        try {
            // Fetch the employee and treatment entities
            Person employee = personService.getPersonById(employeeTreatmentDto.getEmployeeID());
            Treatment treatment = treatmentService.getTreatmentById(employeeTreatmentDto.getTreatmentID());

            // Create the EmployeeTreatment object
            EmployeeTreatment employeeTreatment = new EmployeeTreatment();
            employeeTreatment.setEmployee(employee);
            employeeTreatment.setTreatment(treatment);

            // Save the EmployeeTreatment
            EmployeeTreatment savedEmployeeTreatment = employeeTreatmentService.saveEmployeeTreatment(employeeTreatment);

            // Convert to DTO and return response
            EmployeeTreatmentDto savedEmployeeTreatmentDto = employeeTreatmentConverter.convertModelToDto(savedEmployeeTreatment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeTreatmentDto);
        } catch (DataBaseOperationException | ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update Employee Treatment by ID",
            description = "Updates an employee treatment’s details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee Treatment updated"),
                    @ApiResponse(responseCode = "404", description = "Employee Treatment not found", content = @Content)
            })
    @PutMapping(value = "/employee-treatments/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeTreatmentDto> updateEmployeeTreatment(@PathVariable
                                                                        @Parameter(description = "The id of the employee treatment")
                                                                        int id,
                                                                        @RequestBody EmployeeTreatmentDto employeeTreatmentDto) {
        try {
            // Fetch the existing EmployeeTreatment, employee, and treatment
            EmployeeTreatment employeeTreatment = employeeTreatmentService.getEmployeeTreatmentById(id);
            Person employee = personService.getPersonById(employeeTreatmentDto.getEmployeeID());
            Treatment treatment = treatmentService.getTreatmentById(employeeTreatmentDto.getTreatmentID());

            // Update the EmployeeTreatment details
            employeeTreatment.setEmployee(employee);
            employeeTreatment.setTreatment(treatment);

            // Save the updated EmployeeTreatment
            EmployeeTreatment updatedEmployeeTreatment = employeeTreatmentService.updateEmployeeTreatment(employeeTreatment);
            EmployeeTreatmentDto updatedEmployeeTreatmentDto = employeeTreatmentConverter.convertModelToDto(updatedEmployeeTreatment);
            return ResponseEntity.ok(updatedEmployeeTreatmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete Employee Treatment by ID",
            description = "Deletes an employee treatment identified by its id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Employee Treatment successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Employee Treatment not found", content = @Content)
            })
    @DeleteMapping(value = "/employee-treatments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteEmployeeTreatment(@PathVariable
                                                     @Parameter(description = "The id of the employee treatment")
                                                     int id) {
        try {
            employeeTreatmentService.deleteEmployeeTreatmentById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
