package com.warehouse.services;

import com.warehouse.configuration.JwtProvider;
import com.warehouse.dtos.LoginRequestDTO;
import com.warehouse.dtos.OrderItemDto;
import com.warehouse.dtos.UserLoginDTO;
import com.warehouse.dtos.UserRegistrationDTO;
import com.warehouse.entities.OrderItem;
import com.warehouse.entities.User;
import com.warehouse.exception.CustomException;
import com.warehouse.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    private final MappingService mappingService;
    private final JavaMailSender emailSender;

    @Value("${app.password-reset-url}")
    private String passwordResetUrl;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, JwtProvider jwtProvider,
                       MappingService mappingService, JavaMailSender emailSender) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtProvider = jwtProvider;
        this.mappingService = mappingService;
        this.emailSender = emailSender;
    }

    @Override
    public User findByUsernameAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                return user;
            } else {
                throw new CustomException("you have given wrong password, Try again");
            }
        } else {
            throw new CustomException("Check your email again!");
        }
    }

    @Override
    public UserLoginDTO login(LoginRequestDTO loginRequestDTO) {
        logger.info("user is logging in");
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();

        User user = findByUsernameAndPassword(email, password);
        if (user == null) {
            throw new CustomException("No user exists with this email and password!");
        }

        String token = jwtProvider.generateToken(user.getEmail());
        UserLoginDTO userLoginDTO = new UserLoginDTO(user);
        userLoginDTO.setToken(token);

        return userLoginDTO;
    }

    @Override
    public List<UserRegistrationDTO> getAllUsers() {
        logger.info("Getting list of all Users");
        return userRepository.findAll().stream().map(user-> mappingService.mapEntityToDto(user, UserRegistrationDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserRegistrationDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return null;
        }
        logger.info("Getting User -> {}", id);
        return mappingService.mapEntityToDto(user.get(), UserRegistrationDTO.class);
    }

    @Override
    public UserRegistrationDTO addUser(UserRegistrationDTO userRegistrationDTO) {

        logger.info("Encrypting password");
        userRegistrationDTO.setPassword(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));

        logger.info("Creating new User");
        User saved = userRepository.save(mappingService.mapDtoToEntity(userRegistrationDTO, User.class));
        return mappingService.mapEntityToDto(saved, UserRegistrationDTO.class);
    }

    @Override
    public UserRegistrationDTO updateUser(UserRegistrationDTO user) {
        if (user == null) {
            throw new CustomException("The user cannot be null!");
        }

        Optional<User> existingUserOpt = userRepository.findById(user.getId());
        if (existingUserOpt.isEmpty()) {
            throw new CustomException("User with ID " + user.getId() + " not found!");
        }

        User existingUser = existingUserOpt.get();

        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setEmail(user.getEmail());
        existingUser.setRoles(user.getRoles());

        User updatedUser = userRepository.save(existingUser);
        return mappingService.mapEntityToDto(updatedUser, UserRegistrationDTO.class);


    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Delete User -> {}", id);
        this.userRepository.deleteById(id);
    }
    @Override
    public void sendPasswordResetEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        String token = generatePasswordResetToken();

        user.setPasswordResetToken(token);
        userRepository.save(user);

        String resetLink = passwordResetUrl + "?token=" + token;
        sendEmail(email, resetLink);
    }

    private String generatePasswordResetToken() {
        return UUID.randomUUID().toString();
    }

    private void sendEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the following link: " + resetLink);
        emailSender.send(message);
    }
    public User validatePasswordResetToken(String token) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired password reset token"));

        return user;
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user = validatePasswordResetToken(token);
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        user.setPasswordResetToken(null);

        userRepository.save(user);
    }
}
