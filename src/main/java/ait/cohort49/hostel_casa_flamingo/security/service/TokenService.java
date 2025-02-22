package ait.cohort49.hostel_casa_flamingo.security.service;


import ait.cohort49.hostel_casa_flamingo.repository.RoleRepository;
import ait.cohort49.hostel_casa_flamingo.security.AuthInfo;
import ait.cohort49.hostel_casa_flamingo.security.UserDetailServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
public class TokenService {
    private final UserDetailServiceImpl userDetailServiceImpl;
    private SecretKey accessKey;
    private SecretKey refreshKey;

    public TokenService(RoleRepository roleRepository,
                        @Value("${key.access}") String accessSecretPhrase,
                        @Value("${key.refresh}") String refreshSecretPhrase, UserDetailServiceImpl userDetailServiceImpl) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretPhrase));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretPhrase));
        this.userDetailServiceImpl = userDetailServiceImpl;
    }

    public String generateAccessToken(UserDetails user) {
        Instant now = Instant.now();
        Instant expiration = now.plus(10, ChronoUnit.DAYS);

        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(accessKey)
                .claim("roles", user.getAuthorities())
                .compact();
    }

    public String generateRefreshToken(UserDetails user) {

        Instant now = Instant.now();
        Instant expiration = now.plus(15, ChronoUnit.DAYS);

        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshKey);

    }

    private boolean validateToken(String token, SecretKey key) {

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private Claims getClaimsFromToken(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims getAccessClaimsFromToken(String accessToken) {
        return getClaimsFromToken(accessToken, accessKey);
    }

    public Claims getRefreshClaimsFromToken(String refreshToken) {
        return getClaimsFromToken(refreshToken, refreshKey);
    }

    public AuthInfo mapClaimsToAuthInfo(Claims claims) {
        String username = claims.getSubject();
        UserDetails user = userDetailServiceImpl.loadUserByUsername(username);
        return new AuthInfo(username, user.getAuthorities());

    }


}
