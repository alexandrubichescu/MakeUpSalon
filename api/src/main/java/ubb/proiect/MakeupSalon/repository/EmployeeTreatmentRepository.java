package ubb.proiect.MakeupSalon.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface EmployeeTreatmentRepository extends JpaRepository<EmployeeTreatment, Integer>,
        QueryByExampleExecutor<EmployeeTreatment>,
        PagingAndSortingRepository<EmployeeTreatment, Integer> {

    List<EmployeeTreatment> findByTreatmentTreatmentId(int treatmentId);
    List<EmployeeTreatment> findByEmployeePersonId(int employeeId);


}
