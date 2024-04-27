package ubb.proiect.MakeupSalon.repository;

import ubb.proiect.MakeupSalon.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>,
        QueryByExampleExecutor<Appointment>,
        PagingAndSortingRepository<Appointment, Integer> {

}
