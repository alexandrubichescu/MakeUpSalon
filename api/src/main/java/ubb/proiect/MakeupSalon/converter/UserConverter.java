package ubb.proiect.MakeupSalon.converter;

import org.springframework.stereotype.Component;
import ubb.proiect.MakeupSalon.dto.UserDto;
import ubb.proiect.MakeupSalon.model.User;

@Component
public class UserConverter {
    public User convertDtoToModel(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .dateOfBirth(userDto.getDateOfBirth())
                .address(userDto.getAddress())
                .role(userDto.getRole())
                .pictureURL(userDto.getPictureURL())
                .accountNonExpired(userDto.isAccountNonExpired())
                .accountNonLocked(userDto.isAccountNonLocked())
                .credentialsNonExpired(userDto.isCredentialsNonExpired())
                .enabled(userDto.isEnabled())
                .employeeTreatments(userDto.getEmployeeTreatments())
                .customerAppointments(userDto.getCustomerAppointments())
                .employeeAppointments(userDto.getEmployeeAppointments())
                .build();
    }

    public UserDto convertModelToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .address(user.getAddress())
                .role(user.getRole())
                .pictureURL(user.getPictureURL())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .employeeTreatments(user.getEmployeeTreatments())
                .customerAppointments(user.getCustomerAppointments())
                .employeeAppointments(user.getEmployeeAppointments())
                .build();
    }
}
