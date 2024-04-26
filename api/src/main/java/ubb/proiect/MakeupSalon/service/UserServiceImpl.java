package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.exception.UserNotFoundException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Set<Treatment> getTreatmentsByUserId(int id) {
        Optional<User> employee = userRepository.findById(id);
        if(employee.isPresent()) {
            return employee.get().getEmployeeTreatments().stream()
                    .map(EmployeeTreatment::getTreatment)
                    .collect(Collectors.toSet());
        } else {
            throw new UserNotFoundException("Employee with id " + id + " not found");
        }
    }
}
