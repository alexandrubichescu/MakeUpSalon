package ubb.proiect.MakeupSalon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ubb.proiect.MakeupSalon.exception.AuthFailedException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.repository.PersonRepository;
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
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository repository;
    private final PersonRepository personRepository;
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
        log.info("Register request: {}", request);

        var person = Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .pictureUrl(request.getPictureURL())
                .build();
        Person savedPerson = personRepository.save(person);

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .person(savedPerson)
                .build();
        repository.save(user);
        savedPerson.setUser(user);

        var jwtToken = jwtService.generateToken(user);
        log.info("JWT token: {}", jwtToken);
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
        log.info("Authenticate request: {}", request);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new AuthFailedException("Invalid email or password!");
        }
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        log.info("JWT token: {}", jwtToken);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
