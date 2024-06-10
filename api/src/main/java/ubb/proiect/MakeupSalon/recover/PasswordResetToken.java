package ubb.proiect.MakeupSalon.recover;

import jakarta.persistence.*;
import lombok.Data;
import ubb.proiect.MakeupSalon.model.User;

import java.time.LocalDateTime;

@Entity
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDateTime expiryDate;

    // getters and setters
}
