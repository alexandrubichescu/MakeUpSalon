package ubb.proiect.MakeupSalon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.proiect.MakeupSalon.recover.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}

