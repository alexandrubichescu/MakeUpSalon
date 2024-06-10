package ubb.proiect.MakeupSalon.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configures the security filter chain.
     *
     * @param http The HttpSecurity object used to configure security.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
                            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
                            config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                            return config;
                        })
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers("/api/users/recover-password/**").permitAll()
                                .requestMatchers("/api/users/register").permitAll()
                                .requestMatchers("/api/users/login").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("v3/api-docs/**").permitAll()
//                                .requestMatchers("/api/auth/**").permitAll()
//                                .requestMatchers("/api/users/**").permitAll()
//                                .requestMatchers("/api/treatments").permitAll()
//                                .requestMatchers("/api/persons").permitAll()
//                                .requestMatchers("/api/persons/**").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")
//                                .requestMatchers("/api/treatments/**").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")
//                                .requestMatchers("/api/appointments/**").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")
//                                .requestMatchers("/api/appointments/**").authenticated()
                                .anyRequest()
                                .authenticated())
//                .httpBasic(Customizer.withDefaults())
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
