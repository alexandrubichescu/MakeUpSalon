package ubb.proiect.MakeupSalon;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(
        info = @Info(
                title = "MakeUp Salon API",
                version = "v1",
                description = "The “MakeUp Salon” API is a digital gateway to a world " +
                        "of beauty services. It’s designed to streamline appointments, " +
                        "showcase services, and manage client interactions with ease. " +
                        "Tailored for convenience, it connects users to their next beauty " +
                        "adventure with just a few clicks."
        ),
        externalDocs = @ExternalDocumentation(
                description = "API Repository",
                url = "https://github.com/MihaiS-git/MakeUpSalon.git"
        )
)
@SpringBootApplication
public class MakeupSalonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakeupSalonApplication.class, args);
    }

}