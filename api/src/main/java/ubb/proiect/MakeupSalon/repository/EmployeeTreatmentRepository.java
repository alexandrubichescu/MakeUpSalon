package ubb.proiect.MakeupSalon.repository;

import ubb.proiect.MakeupSalon.model.EmployeeTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface EmployeeTreatmentRepository extends JpaRepository<EmployeeTreatment, Integer>,
        QueryByExampleExecutor<EmployeeTreatment>,
        PagingAndSortingRepository<EmployeeTreatment, Integer> {

}
