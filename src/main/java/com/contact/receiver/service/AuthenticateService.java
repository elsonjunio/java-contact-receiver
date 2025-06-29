package com.contact.receiver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticateService {

    private static final String HEADER_BEARER = "Bearer ";
    private static final String HEADER_ATHORIZATION = "Authorization";
    private static final String BASE64ENCODEDSECRETKEY = "signinKey";
    private static final String CLAIMS_KEY = "authorities";
    public static final long EXPIRATION_TOKEN_1_HOUR = 3600000L;

    static public String generateJWTToken(Authentication authentication, long expirationMillis) {

        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIMS_KEY,
                authentication
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SignatureAlgorithm.HS512, BASE64ENCODEDSECRETKEY)
                .addClaims(claims)
                .compact();
    }

    static public void addJWTToken(HttpServletResponse response, Authentication authentication) {
        String jwtToken = generateJWTToken(authentication, EXPIRATION_TOKEN_1_HOUR);
        response.addHeader(HEADER_ATHORIZATION, HEADER_BEARER + jwtToken);
        response.addHeader("Access-Control-Expose-Headers", HEADER_ATHORIZATION);
    }

    static public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_ATHORIZATION);

        if (token != null) {
            Claims user = Jwts.parser()
                    .setSigningKey(BASE64ENCODEDSECRETKEY)
                    .parseClaimsJws(token.replace(HEADER_BEARER, ""))
                    .getBody();

            if (user != null) {

                Object rolesObject = user.get(CLAIMS_KEY);
                List<SimpleGrantedAuthority> roles = new ArrayList<>();

                if (rolesObject instanceof List<?>) {
                    roles = ((List<?>) rolesObject).stream()
                            .filter(String.class::isInstance)
                            .map(String.class::cast)
                            .map(SimpleGrantedAuthority::new)
                            .toList();
                }

                return new UsernamePasswordAuthenticationToken(user, null, roles);
            }

            throw new RuntimeException("Authentication failed.");

        }

        return null;
    }

}
