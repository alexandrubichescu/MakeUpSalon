package ubb.proiect.MakeupSalon.repository;

import ubb.proiect.MakeupSalon.model.Role;
import ubb.proiect.MakeupSalon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>,
        QueryByExampleExecutor<User>,
        PagingAndSortingRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
}