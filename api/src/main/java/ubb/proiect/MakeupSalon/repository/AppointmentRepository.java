package ubb.proiect.MakeupSalon.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ubb.proiect.MakeupSalon.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>,
        QueryByExampleExecutor<Appointment>,
        PagingAndSortingRepository<Appointment, Integer> {

    @Query("SELECT a FROM Appointment a WHERE a.employee.personId = :employeeId")
    List<Appointment> findByEmployeeId(@Param("employeeId") int employeeId);
}
