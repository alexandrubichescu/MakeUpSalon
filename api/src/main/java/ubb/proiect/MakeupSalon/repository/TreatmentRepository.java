package ubb.proiect.MakeupSalon.repository;

import ubb.proiect.MakeupSalon.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface TreatmentRepository extends JpaRepository<Treatment, Integer>,
        QueryByExampleExecutor<Treatment>,
        PagingAndSortingRepository<Treatment, Integer> {

}
