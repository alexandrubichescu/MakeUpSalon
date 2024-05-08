package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import ubb.proiect.MakeupSalon.repository.EmployeeTreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeTreatmentServiceImpl implements IEmployeeTreatmentService{

    @Autowired
    private EmployeeTreatmentRepository employeeTreatmentRepository;

    @Override
    public List<EmployeeTreatment> getAllEmployeeTreatments() {
        return employeeTreatmentRepository.findAll();
    }

    @Override
    public EmployeeTreatment getEmployeeTreatmentById(int id) {
        return employeeTreatmentRepository.getReferenceById(id);
    }

    @Override
    public EmployeeTreatment saveEmployeeTreatment(EmployeeTreatment employeeTreatment) {
        return employeeTreatmentRepository.save(employeeTreatment);
    }

    @Override
    public EmployeeTreatment updateEmployeeTreatment(EmployeeTreatment employeeTreatment) {
        return employeeTreatmentRepository.save(employeeTreatment);
    }

    @Override
    public void deleteEmployeeTreatmentById(int id) {
        employeeTreatmentRepository.deleteById(id);
    }

}
