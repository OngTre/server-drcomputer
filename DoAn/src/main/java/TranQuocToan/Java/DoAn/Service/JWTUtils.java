package TranQuocToan.Java.DoAn.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.function.Function;

@Component
public class JWTUtils {

    private SecretKey Key;
    private static final long EXPIRATION_TIME = 86400000;  // 24 hours in milliseconds

    public JWTUtils() {
        String secreteString = "5754660ec3d43a9cecccf5cc131b08dcd65f41aee8083ce9b6013f33937857da";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(
                Jwts.parserBuilder()  // Sử dụng parserBuilder thay vì parser
                        .setSigningKey(Key)  // Thiết lập khóa chữ ký
                        .build()
                        .parseClaimsJws(token)  // Dùng parseClaimsJws thay vì parseSignedClaims
                        .getBody()  // Lấy payload từ Claims
        );
    }

    public boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token, Claims::getExpiration);

        // Lấy thời gian hiện tại theo múi giờ Ấn Độ (IST - UTC +5:30)
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
        long currentTimeMillis = calendar.getTimeInMillis();

        // Độ lệch múi giờ (90 phút cho phép)
        long allowedSkewMillis = 90 * 60 * 1000;  // 90 phút * 60 giây * 1000 ms

        // Kiểm tra nếu thời gian hết hạn token đã vượt quá thời gian hiện tại, cộng với khoảng lệch đồng hồ cho phép
        return expiration.getTime() + allowedSkewMillis < currentTimeMillis;
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
