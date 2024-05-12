package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.UserDto;
import ubb.proiect.MakeupSalon.model.User;

@Component
public class UserConverter {
    public User convertDtoToModel(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
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
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .build();
    }
}
