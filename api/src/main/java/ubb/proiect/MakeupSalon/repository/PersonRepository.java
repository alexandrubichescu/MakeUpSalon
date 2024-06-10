package ubb.proiect.MakeupSalon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.proiect.MakeupSalon.model.Person;


public interface PersonRepository extends JpaRepository<Person, Integer>,
        QueryByExampleExecutor<Person>,
        PagingAndSortingRepository<Person, Integer> {
}
