package ubb.proiect.MakeupSalon.controller;

import ubb.proiect.MakeupSalon.exception.UserNotFoundException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.repository.UserRepository;
import ubb.proiect.MakeupSalon.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/users")
public class AuthUser {

    private final AuthenticationService service;
    private final UserRepository userJpaRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthUser(AuthenticationService service, UserRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PatchMapping("/recover-password/{id}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, @PathVariable int id){
        Optional<User> getUser = userJpaRepository.findById(id);
        if (getUser.isEmpty()) throw new UserNotFoundException("User with id: " + id + " doest not exist!");

        User user = getUser.get();

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userJpaRepository.save(user);

        return ResponseEntity.ok(user);
    }
}