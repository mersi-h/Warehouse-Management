package com.warehouse.dtos;

import com.warehouse.entities.User;
import com.warehouse.entities.UserRole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserLoginDTO {
    private Long id;
    private String email;
    private String fullName;
    private UserRole role;
    private String token;

    public UserLoginDTO (User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getName()+" "+user.getSurname();
        this.role = user.getRoles();
    }
}
