package ubb.proiect.MakeupSalon.service;

import ubb.proiect.MakeupSalon.exception.AuthFailedException;
import ubb.proiect.MakeupSalon.model.AuthenticationRequest;
import ubb.proiect.MakeupSalon.model.AuthenticationResponse;
import ubb.proiect.MakeupSalon.model.RegisterRequest;
import ubb.proiect.MakeupSalon.model.User;
import ubb.proiect.MakeupSalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user.
     *
     * @param request The register request containing user information.
     * @return An AuthenticationResponse object containing the JWT token.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .role(request.getRole())
                .pictureURL(request.getPictureURL())
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user.
     *
     * @param request The authentication request containing user credentials.
     * @return An AuthenticationResponse object containing the JWT token.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new AuthFailedException("Invalid email or password!");
        }
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
