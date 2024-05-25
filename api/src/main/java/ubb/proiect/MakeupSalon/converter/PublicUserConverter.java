package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.PublicUserDto;
import ubb.proiect.MakeupSalon.model.User;

@Component
public class PublicUserConverter {
    public User convertDtoToModel(PublicUserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();
    }

    public PublicUserDto convertModelToDto(User user) {
        return PublicUserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
