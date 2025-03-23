package pl.maropce.etutor.config.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String JWT_SECRET = "ba67831e2342e9c768c0b8954a33f28a9f156babbb80e80cc0f1614fcd07d4ec3f4fbabc636ee5f1777af1ca219372a12c3ec4df4b8715202fdd82a73badc4423060a0608eb8c8d8c7a77d7361c79dc32601e6ce33d25e2be0f7dcbeca2120194ec47ddbbbfe04b0fa96a0425e31d3f5ea385b533fcf9edcec78d91f50cc2495a4d39f233fb39e7cc660096c271b364b53f0db356d748420b0596ef23ccffa5d27822224c2156a9290d6ac12b09aec2c5ebfe793b370fca99b4f0f87bd690fd3fbbe637d0f400ee383ecf5f910fadb03be01a3ebb0967064f6e7fb71fd31f9d4eb1de13cc01a6e017307a7db5da04a535fcf8d9de9b58ba3f79fee98a8938ba1";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build();

        return jwtParser.parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    private Date extractExpiration(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build();
        return jwtParser.parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }



    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }




}
