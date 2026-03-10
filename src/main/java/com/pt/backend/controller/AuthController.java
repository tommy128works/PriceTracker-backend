package com.pt.backend.controller;


import com.pt.backend.domain.RefreshToken;
import com.pt.backend.domain.User;
import com.pt.backend.dto.auth.JwtResponse;
import com.pt.backend.dto.auth.AuthenticateUserRequest;
import com.pt.backend.dto.user.CreateUserRequest;
import com.pt.backend.dto.user.UserView;
import com.pt.backend.security.JwtService;
import com.pt.backend.security.RefreshTokenService;
import com.pt.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserService userService,
            RefreshTokenService refreshTokenService
    ) throws Exception {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserView create(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody AuthenticateUserRequest request,
            HttpServletResponse response
    ) throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(user);
        refreshTokenService.createToken(user, response);

        return ResponseEntity.ok(
                new JwtResponse(accessToken)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) throws Exception {

        RefreshToken oldToken = refreshTokenService.verifyToken(refreshToken);
        User user = oldToken.getUser();

        String accessToken = jwtService.generateToken(user);
        refreshTokenService.createToken(user, response);

        return ResponseEntity.ok(
                new JwtResponse(accessToken)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) throws Exception {

        if (refreshToken != null) {
            refreshTokenService.deleteToken(refreshToken);
        }

        refreshTokenService.deleteCookie(response);

        return ResponseEntity.ok().build();
    }

}