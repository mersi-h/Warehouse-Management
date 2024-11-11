package com.warehouse.services;

import com.warehouse.dtos.LoginRequestDTO;
import com.warehouse.dtos.UserLoginDTO;
import com.warehouse.dtos.UserRegistrationDTO;
import com.warehouse.entities.User;

import java.util.List;

public interface IUserService {

    User findByUsernameAndPassword(String email, String password);

    UserLoginDTO login(LoginRequestDTO loginRequestDTO);

    List<UserRegistrationDTO> getAllUsers();

    UserRegistrationDTO getUserById(Long id);

    UserRegistrationDTO addUser(UserRegistrationDTO userRegistrationDTO);

    UserRegistrationDTO updateUser(UserRegistrationDTO user);

    void deleteUser(Long id);

    void sendPasswordResetEmail(String email);

    void resetPassword(String token, String newPassword);
}
