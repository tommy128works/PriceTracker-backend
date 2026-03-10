package com.pt.backend.security;


import com.pt.backend.domain.RefreshToken;
import com.pt.backend.domain.User;
import com.pt.backend.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final String HMAC_ALGO = "HmacSHA256";
    private final byte[] secretKey;
    private final int expirationDays;

    public RefreshTokenService(
            RefreshTokenRepository repository,
            @Value("${jwt.refresh.hmac.secret}") String secret,
            @Value("${jwt.refresh.expiration}") int expirationDays
    ) {
        this.repository = repository;
        this.secretKey = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationDays = expirationDays;
    }

    public String createToken(
            User user,
            HttpServletResponse response
    ) throws Exception {

        String rawToken = generateToken();

        RefreshToken tokenHash = RefreshToken.builder()
                .tokenHash(hashToken(rawToken))
                .expiryDate(Instant.now().plus(expirationDays, ChronoUnit.DAYS))
                .user(user)
                .build();
        repository.save(tokenHash);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", rawToken)
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(Duration.ofDays(expirationDays))
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return rawToken;
    }

    public String generateToken() {
        byte[] randomBytes = new byte[64];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public String hashToken(String token) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey, HMAC_ALGO);
        mac.init(keySpec);
        byte[] hmac = mac.doFinal(token.getBytes());
        return Base64.getEncoder().encodeToString(hmac);
    }

    public RefreshToken verifyToken(String refreshToken) throws Exception {
        String tokenHash = hashToken(refreshToken);

        RefreshToken storedTokenHash = repository
                .findByTokenHash(tokenHash)
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        repository.delete(storedTokenHash);

        if (storedTokenHash.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        return storedTokenHash;
    }

}