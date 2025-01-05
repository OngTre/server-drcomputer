package TranQuocToan.Java.DoAn.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.Duration;

@Component
public class JWTUtils {
    private SecretKey Key;
    private static final long EXPIRATION_TIME = 432000000; // 24 hours in milliseconds
    private static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    public JWTUtils() {
        String secretString = "5754660ec3d43a9cecccf5cc131b08dcd65f41aee8083ce9b6013f33937857da";
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails) {
        ZonedDateTime vietnamNow = ZonedDateTime.now(VIETNAM_ZONE);
        Date currentDate = Date.from(vietnamNow.toInstant());
        // Sử dụng plus với Duration
        Date expirationDate = Date.from(
                vietnamNow.plus(Duration.ofMillis(EXPIRATION_TIME)).toInstant()
        );

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(Key)
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        ZonedDateTime vietnamNow = ZonedDateTime.now(VIETNAM_ZONE);
        Date currentDate = Date.from(vietnamNow.toInstant());
        // Sử dụng plus với Duration
        Date expirationDate = Date.from(
                vietnamNow.plus(Duration.ofMillis(EXPIRATION_TIME)).toInstant()
        );

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(Key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(
                Jwts.parser()
                        .verifyWith(Key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
        );
    }

    public boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token, Claims::getExpiration);
        ZonedDateTime vietnamNow = ZonedDateTime.now(VIETNAM_ZONE);
        Date currentDate = Date.from(vietnamNow.toInstant());

        // Thêm khoảng thời gian dung sai
        long allowedSkewMillis = 90 * 60 * 1000; // 90 phút

        return expiration.getTime() + allowedSkewMillis < currentDate.getTime();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
