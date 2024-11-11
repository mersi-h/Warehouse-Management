package com.warehouse.dtos;

import com.warehouse.entities.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    private String surname;

    @Email(message = "invalid email address")
    @NotBlank(message = "email field cannot be empty")
    private String email;

    @NotBlank(message = "Password field cannot be empty")
    private String password;

    @NotBlank(message = "Role field cannot be empty")
    private UserRole roles;

}
