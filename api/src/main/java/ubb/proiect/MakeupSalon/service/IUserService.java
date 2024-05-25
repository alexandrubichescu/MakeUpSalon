package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.model.User;

import java.util.List;

public interface IUserService {
    List<User>  getAllUsers();
    User getUserById(int id);
    User updateUser(int id, User user);
    void deleteUserById(int id);

    List<User> getUsersByRole(String roleString);
    User getUserByEmail(String email);
}