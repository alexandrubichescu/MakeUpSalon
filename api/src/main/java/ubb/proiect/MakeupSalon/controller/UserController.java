package ubb.proiect.MakeupSalon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ubb.proiect.MakeupSalon.converter.UserConverter;
import ubb.proiect.MakeupSalon.dto.UserDto;
import ubb.proiect.MakeupSalon.exception.DataBaseOperationException;
import ubb.proiect.MakeupSalon.exception.ResourceNotFoundException;
import ubb.proiect.MakeupSalon.model.*;
import ubb.proiect.MakeupSalon.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserConverter userConverter;


    @Operation(summary = "Find all Users",
            description = "Retrieves a comprehensive list of users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users found"),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content)
            })
    @GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> usersDto = users.stream()
                    .map(user -> userConverter.convertModelToDto(user))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(usersDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Find User by ID",
            description = "Retrieves a user by their id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found"),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    @GetMapping(value="/users/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserById(@PathVariable
                                               @Parameter(description = "The id of the user")
                                               int id) {
        try {
            User user = userService.getUserById(id);
            UserDto userDto = userConverter.convertModelToDto(user);
            return ResponseEntity.ok(userDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Find Users by role",
            description = "Retrieves a comprehensive list of users by their role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users found"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content)
            })
    @GetMapping(value = "/users/role/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable
                                                        @Parameter(description = "The role of the user")
                                                        String role) {
        try {
            List<User> users = userService.getUsersByRole(role);
            List<UserDto> userDtos = users.stream()
                    .map(user -> userConverter.convertModelToDto(user))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataBaseOperationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update an existing User by ID",
            description = "Update an existing user’s information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated"),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    @PutMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@PathVariable
                                              @Parameter(description = "The id of the user")
                                              int id,
                                              @RequestBody UserDto userDto) {
        try {
            User updatedUser = userService.updateUser(id, userConverter.convertDtoToModel(userDto));
            UserDto updatedUserDto = userConverter.convertModelToDto(updatedUser);
            return ResponseEntity.ok(updatedUserDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an existing User by ID",
            description = "Remove a user by their ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            })
    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable
                                        @Parameter(description = "The id of the user")
                                        int id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Find User by email",
            description = "Retrieves a user(Optional) by their email address")
    @GetMapping("/users/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable @Parameter(description = "The email of the user") String email) {
        try {
            User checkUser = userService.getUserByEmail(email);
            UserDto userDto = userConverter.convertModelToDto(checkUser);
            return ResponseEntity.ok(userDto);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


}
