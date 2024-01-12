package com.spring.recipeapp.config;

import com.spring.recipeapp.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class JwtTokenService {
    private static final long EXPIRATIONTIME = Duration.ofDays(3).toMillis();

    private static final String HEADER_STRING = "app-auth";
    private static final String CLAIM_USER = "email";
    @Value("${application.secret}")
    private String secret;

    public Authentication getAuthentication(final HttpServletRequest request) {
        Authentication auth = null;
        // we still need the old header for various external services calling gfs
        final String token = Optional.ofNullable(request.getHeader(HEADER_STRING)).orElse(null);
        try {
            // use token != null && token.isBlack() if jdk11
            if (token != null && !("").equals(token)) {
                Jws<Claims> claims;
                claims = Jwts.parserBuilder().setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token);
                if (null != claims) {
                    // Check if there is a email present
                    final String email = Optional//
                            .ofNullable(claims.getBody().get(CLAIM_USER))//
                            .map(Object::toString)//
                            .orElseThrow(
                                    () -> new AuthenticationCredentialsNotFoundException("No email given in jwt"));
                    auth = new UsernamePasswordAuthenticationToken(email, null, null);
                }
            }

        } catch (final MalformedJwtException | UnsupportedJwtException ex) {
            log.error("Unsupported jwt token {} with exception {}",
                    token,
                    ex.getMessage());
            throw new JwtAuthenticationException(ex);
        } catch (final ExpiredJwtException ex) {
            log.error("Expired jwt token {}",
                    ex.getMessage());
            throw new JwtAuthenticationException(ex);
        } catch (final AuthenticationCredentialsNotFoundException ex) {
            log.error("An error occured while trying to create authentication based on jwt token, missing credentials {}",
                    ex.getMessage());
            throw new JwtAuthenticationException(ex);
        } catch (final Exception ex) {
            log.error("Unexpected exception occured while parsing jwt {} exception: {}",
                    token,
                    ex.getMessage());
            throw new JwtAuthenticationException(ex);
        }

        log.debug("The authentication constructed by the JwtService");
        return auth;
    }


    public String createJwtToken(final String email) {
        String jwtToken;
        jwtToken = Jwts.builder()//
                .claim(CLAIM_USER, email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))//
                .signWith(getSigningKey())
                .compact();
        return jwtToken;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
