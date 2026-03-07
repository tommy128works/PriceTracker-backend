package com.pt.backend.controller;


import com.pt.backend.domain.RefreshToken;
import com.pt.backend.domain.User;
import com.pt.backend.dto.auth.JwtResponse;
import com.pt.backend.dto.auth.AuthenticateUserRequest;
import com.pt.backend.dto.auth.RefreshRequest;
import com.pt.backend.dto.user.CreateUserRequest;
import com.pt.backend.dto.user.UserView;
import com.pt.backend.repository.RefreshTokenRepository;
import com.pt.backend.security.JwtService;
import com.pt.backend.security.RefreshTokenService;
import com.pt.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserService userService,
            RefreshTokenService refreshTokenService,
            RefreshTokenRepository refreshTokenRepository
    ) throws Exception {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserView create(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthenticateUserRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(
                new JwtResponse(accessToken, refreshToken.getToken())
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@Valid @RequestBody RefreshRequest request) {

        RefreshToken token = refreshTokenRepository
                .findByToken(request.refreshToken())
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        refreshTokenService.verifyExpiration(token);

        User user = token.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(
                new JwtResponse(accessToken, token.getToken())
        );
    }

}