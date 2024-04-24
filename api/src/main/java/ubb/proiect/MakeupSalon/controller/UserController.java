package ubb.proiect.MakeupSalon.controller;

import ubb.proiect.MakeupSalon.exception.UserNotFoundException;
import ubb.proiect.MakeupSalon.model.User;
import ubb.proiect.MakeupSalon.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        Optional<User> checkUser = userService.getUserByEmail(email);
        if (checkUser.isEmpty()) throw new UserNotFoundException("User with email " + email + " not found");
        return checkUser;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/users/{email}")
    public User updateUser(@PathVariable String email, @RequestBody User user) {
        Optional<User> checkUser = userService.getUserByEmail(email);
        if (checkUser.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found");
        } else {
            User updatedUser = checkUser.get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            updatedUser.setDateOfBirth(user.getDateOfBirth());
            updatedUser.setAddress(user.getAddress());
            updatedUser.setRole(user.getRole());
            updatedUser.setPictureURL(user.getPictureURL());
            userService.updateUser(updatedUser);
            return updatedUser;
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
