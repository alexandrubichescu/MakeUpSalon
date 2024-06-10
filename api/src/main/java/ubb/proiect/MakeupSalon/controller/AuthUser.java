package ubb.proiect.MakeupSalon.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import ubb.proiect.MakeupSalon.email.GmailIntegration;
import ubb.proiect.MakeupSalon.email.GmailQuickstart;
import ubb.proiect.MakeupSalon.exception.UserNotFoundException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.recover.PasswordResetToken;
import ubb.proiect.MakeupSalon.recover.TokenGenerator;
import ubb.proiect.MakeupSalon.repository.PasswordResetTokenRepository;
import ubb.proiect.MakeupSalon.repository.UserRepository;
import ubb.proiect.MakeupSalon.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/auth")
public class AuthUser {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private final AuthenticationService service;
    private final UserRepository userJpaRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthUser(AuthenticationService service, UserRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Register a new User",
            description = "Creates and registers a new user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully registered")
            })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @Operation(summary = "Login an existing User",
            description = "Authenticates an existing user with valid credentials",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully authenticated")
            })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Operation(summary = "Password recovery",
            description = "Change password for an existing user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully changed password")
            })
    @PatchMapping("/recover-password/id/{id}")
    public ResponseEntity<?> changePasswordById(@RequestBody ChangePasswordRequest request, @PathVariable int id) {
        Optional<User> getUser = userJpaRepository.findById(id);
        if (getUser.isEmpty()) {
            throw new UserNotFoundException("User with id: " + id + " doest not exist!");
        }

        User user = getUser.get();

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userJpaRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Password recovery",
            description = "Change password for an existing user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully changed password")
            })
    @PatchMapping("/recover-password/email/{email}")
    public ResponseEntity<?> changePasswordByEmail(@RequestBody ChangePasswordRequest request, @PathVariable String email) {
        Optional<User> getUser = userJpaRepository.findByEmail(email);
        if (getUser.isEmpty()) {
            throw new UserNotFoundException("User with email: " + email + " doest not exist!");
        }

        User user = getUser.get();

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same!");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userJpaRepository.save(user);

        return ResponseEntity.ok(user);
    }

//    @PatchMapping("/recover-password/email/{email}")
//    public ResponseEntity<?> changePasswordByEmail(@PathVariable String email) throws MessagingException, IOException, GeneralSecurityException {
//        Optional<User> getUser = userJpaRepository.findByEmail(email);
//        if (getUser.isEmpty()) {
//            throw new UserNotFoundException("User with email: " + email + " does not exist!");
//        }
//
//        User user = getUser.get();
//        String token = TokenGenerator.generateToken();
//
//        PasswordResetToken resetToken = new PasswordResetToken();
//        resetToken.setToken(token);
//        resetToken.setUser(user);
//        resetToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // Token valid for 24 hours
//        passwordResetTokenRepository.save(resetToken);
//
//        String resetLink = "http://localhost:4200/recover/reset-password/" + token;
//        sendResetEmail(email, resetLink);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Password reset link sent to your email.");
//
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//    private void sendResetEmail(String to, String link) throws MessagingException, IOException, GeneralSecurityException {
//        String from = "ingsuciumihai@gmail.com";
//        String subject = "Password Reset Request";
//        String bodyText = "To reset your password, click the link below:\n" + link;
//
//        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        Gmail gmailService = new Gmail.Builder(HTTP_TRANSPORT, GmailQuickstart.JSON_FACTORY, GmailQuickstart.getCredentials(HTTP_TRANSPORT))
//                .setApplicationName(GmailQuickstart.APPLICATION_NAME)
//                .build();
//
//        GmailIntegration gmailIntegration = new GmailIntegration(gmailService);
//        gmailIntegration.sendEmail("me", to, from, subject, bodyText);
//    }

    @PatchMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @RequestBody ChangePasswordRequest request) {
        Optional<PasswordResetToken> resetTokenOpt = passwordResetTokenRepository.findByToken(token);
        if (resetTokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }

        PasswordResetToken resetToken = resetTokenOpt.get();
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has expired");
        }

        User user = resetToken.getUser();
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same!");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userJpaRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password successfully changed.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}