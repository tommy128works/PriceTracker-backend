package com.pt.backend.service;


import com.pt.backend.domain.User;
import com.pt.backend.dto.user.AuthenticateUserRequest;
import com.pt.backend.dto.user.CreateUserRequest;
import com.pt.backend.dto.user.UpdateUserRequest;
import com.pt.backend.dto.user.UserView;
import com.pt.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserView create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User saved = userRepository.save(user);
        return toView(saved);
    }

    public UserView getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return toView(user);
    }

    public List<UserView> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toView)
                .toList();
    }

    public UserView update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.updateName(request.firstName(), request.lastName());

        return toView(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserView toView(User user) {
        return new UserView(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}