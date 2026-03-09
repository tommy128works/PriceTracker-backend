package com.pt.backend.security;


import com.pt.backend.domain.RefreshToken;
import com.pt.backend.domain.User;
import com.pt.backend.dto.auth.RefreshRequest;
import com.pt.backend.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final String HMAC_ALGO = "HmacSHA256";
    private final byte[] SECRET_KEY;

    public RefreshTokenService(
            RefreshTokenRepository repository,
            @Value("${hmac.secret}") String secret
    ) {
        this.repository = repository;
        this.SECRET_KEY = secret.getBytes(StandardCharsets.UTF_8);
    }

    public String createToken(User user) throws Exception {
        String rawToken = generateToken();

        RefreshToken tokenHash = RefreshToken.builder()
                .tokenHash(hashToken(rawToken))
                .expiryDate(Instant.now().plus(16, ChronoUnit.DAYS))
                .user(user)
                .build();
        repository.save(tokenHash);

        return rawToken;
    }

    public String generateToken() {
        byte[] randomBytes = new byte[64];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public String hashToken(String token) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, HMAC_ALGO);
        mac.init(keySpec);
        byte[] hmac = mac.doFinal(token.getBytes());
        return Base64.getEncoder().encodeToString(hmac);
    }

    public RefreshToken verifyToken(RefreshRequest request) throws Exception {
        String tokenHash = hashToken(request.refreshToken());

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