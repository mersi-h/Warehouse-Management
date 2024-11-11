package com.warehouse.controllers;


import com.warehouse.configuration.JwtProvider;
import com.warehouse.dtos.LoginRequestDTO;
import com.warehouse.dtos.OrderDto;
import com.warehouse.dtos.UserRegistrationDTO;
import com.warehouse.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    private IUserService userService;
    private JwtProvider jwtProvider;

    public UserController(IUserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<UserRegistrationDTO>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }


    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserRegistrationDTO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @PostMapping(value = "/add-user")
    public ResponseEntity<UserRegistrationDTO> addUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userRegistrationDTO));
    }

    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @PutMapping(value = "/update")
    public ResponseEntity<UserRegistrationDTO> updateUser(@RequestBody UserRegistrationDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(user));
    }

    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @DeleteMapping(value = "/delete-user/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return new ResponseEntity<>(userService.login(loginRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        try {
            userService.sendPasswordResetEmail(email);
            return ResponseEntity.ok("Password reset email sent.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending password reset email.");
        }
    }

    @GetMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestParam("new-pass") String newPassword) {
        userService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successful");
    }

}
