package com.contact.receiver.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.contact.receiver.dto.LoginResponseDTO;
import com.contact.receiver.entity.AuthenticatedUser;
import com.contact.receiver.service.AuthenticateService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        AuthenticatedUser authenticatedUser = new ObjectMapper().readValue(collect, AuthenticatedUser.class);

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(authenticatedUser.getUsername(),
                        authenticatedUser.getPassword(),
                        Collections.emptyList()));

    }

    //@Override
    //protected void successfulAuthentication(HttpServletRequest httpServletRequest,
    //        HttpServletResponse httpServletResponse, FilterChain filterChain, Authentication authentication) {
    //        //Retorna apenas Authorization no head
    //        AuthenticateService.addJWTToken(httpServletResponse, authentication);
    //
    //}

    @Override
    protected void successfulAuthentication(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, FilterChain filterChain, Authentication authentication) throws IOException {

        String token = AuthenticateService.generateJWTToken(authentication, AuthenticateService.EXPIRATION_TOKEN_1_HOUR);
        long expiresAt = System.currentTimeMillis() + AuthenticateService.EXPIRATION_TOKEN_1_HOUR;

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Header (mantido)
        httpServletResponse.addHeader("Authorization", "Bearer " + token);
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Authorization");

        // Body (JSON)
        LoginResponseDTO loginResponse = LoginResponseDTO.builder()
        .token("Bearer " + token)
        .username(authentication.getName())
        .roles(roles)
        .expiresAt(expiresAt)
        .build();

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();

        httpServletResponse.getWriter().write(mapper.writeValueAsString(loginResponse));

    }

}
