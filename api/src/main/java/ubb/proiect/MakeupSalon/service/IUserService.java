package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User>  getAllUsers();
    User getUserById(int id);
    User saveUser(User user);
    User updateUser(User user);
    void deleteUserById(int id);

    Optional<User> getUserByEmail(String email);
}

