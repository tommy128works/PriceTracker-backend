package com.pt.backend.controller;


import com.pt.backend.dto.user.AuthenticateUserRequest;
import com.pt.backend.dto.user.CreateUserRequest;
import com.pt.backend.dto.user.UpdateUserRequest;
import com.pt.backend.dto.user.UserView;
import com.pt.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserView create(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public UserView getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserView> getAll() {
        return userService.getAll();
    }

    @PutMapping("/{id}")
    public UserView update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}