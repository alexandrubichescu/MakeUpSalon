package ubb.proiect.MakeupSalon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ubb.proiect.MakeupSalon.model.Role;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class PublicUserDto implements Serializable {
    private int userId;
    private String email;
    private Role role;
    private PersonDto personDto;

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", personDto=" + personDto +
                '}';
    }
}
