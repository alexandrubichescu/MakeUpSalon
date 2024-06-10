package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.UserDto;
import ubb.proiect.MakeupSalon.model.User;
import ubb.proiect.MakeupSalon.service.IPersonService;

@Component
public class UserConverter {

    private final IPersonService personService;

    public UserConverter(IPersonService personService) {
        this.personService = personService;
    }

    public User convertDtoToModel(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .person(personService.getPersonById(userDto.getPersonId()))
                .accountNonExpired(userDto.isAccountNonExpired())
                .accountNonLocked(userDto.isAccountNonLocked())
                .credentialsNonExpired(userDto.isCredentialsNonExpired())
                .enabled(userDto.isEnabled())
                .build();
    }

    public UserDto convertModelToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .personId(user.getPerson().getPersonId())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .build();
    }
}
