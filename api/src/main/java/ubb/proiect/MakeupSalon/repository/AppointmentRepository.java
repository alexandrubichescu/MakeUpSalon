package ubb.proiect.MakeupSalon.repository;

import ubb.proiect.MakeupSalon.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.proiect.MakeupSalon.model.Person;
import ubb.proiect.MakeupSalon.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>,
        QueryByExampleExecutor<Appointment>,
        PagingAndSortingRepository<Appointment, Integer> {

    List<Appointment> findByEmployeeAndStartDateTimeBetween(Person employee, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
