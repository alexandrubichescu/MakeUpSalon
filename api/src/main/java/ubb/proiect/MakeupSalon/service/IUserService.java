package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.Role;
import ubb.proiect.MakeupSalon.model.Treatment;
import ubb.proiect.MakeupSalon.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserService {
    List<User>  getAllUsers();
    User getUserById(int id);
    User saveUser(User user);
    User updateUser(int id, User user);
    void deleteUserById(int id);

    Optional<User> getUserByEmail(String email);
    Set<Treatment> getTreatmentsByUserId(int id);
    List<User> getUsersByRole(Role role);
}