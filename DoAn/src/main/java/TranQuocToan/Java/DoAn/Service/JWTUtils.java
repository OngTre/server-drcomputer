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
import java.util.Calendar;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.function.Function;
import java.security.SecureRandom;


@Component
public class JWTUtils {

    private SecretKey Key;
    private static  long EXPIRATION_TIME = 432000000;  //24hours

    public JWTUtils() {


        String secreteString = "5754660ec3d43a9cecccf5cc131b08dcd65f41aee8083ce9b6013f33937857da";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");

    }

    public String generateToken(UserDetails userDetails){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long currentTimeMillis = calendar.getTimeInMillis();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }
