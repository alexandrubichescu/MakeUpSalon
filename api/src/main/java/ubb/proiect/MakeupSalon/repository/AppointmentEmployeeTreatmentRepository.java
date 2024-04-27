package ubb.proiect.MakeupSalon.repository;

import ubb.proiect.MakeupSalon.model.AppointmentEmployeeTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface AppointmentEmployeeTreatmentRepository extends JpaRepository<AppointmentEmployeeTreatment, Integer>,
        QueryByExampleExecutor<AppointmentEmployeeTreatment>,
        PagingAndSortingRepository<AppointmentEmployeeTreatment, Integer> {

}
