package com.medical.app.user.factory;

import com.medical.app.user.entity.Role;
import com.medical.app.user.entity.User;
import com.medical.app.user.validator.PasswordValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class UserFactory {

    private final PasswordValidator validator;
    private final BCryptPasswordEncoder passwordEncoder;

    public User createDoctorUser(String email, String rawPassword) {
        return buildUser(email,rawPassword, Role.DOCTOR);
    }

    public User createPatientUser(String email, String rawPassword) {
        return buildUser(email,rawPassword, Role.PATIENT);
    }

    private User buildUser(String email, String rawPassword, Role role) {
        validator.validatePasswordStrength(rawPassword);
        String passwordHash = passwordEncoder.encode(rawPassword);
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRole(role);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

}

