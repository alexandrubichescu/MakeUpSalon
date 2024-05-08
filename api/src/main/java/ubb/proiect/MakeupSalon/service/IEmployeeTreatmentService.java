package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.EmployeeTreatment;

import java.util.List;

public interface IEmployeeTreatmentService {
    List<EmployeeTreatment> getAllEmployeeTreatments();
    EmployeeTreatment getEmployeeTreatmentById(int id);
    EmployeeTreatment saveEmployeeTreatment(EmployeeTreatment employeeTreatment);
    EmployeeTreatment updateEmployeeTreatment(EmployeeTreatment employeeTreatment);
    void deleteEmployeeTreatmentById(int id);

}
