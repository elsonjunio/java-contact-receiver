package com.contact.receiver.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

    @Override
    protected void successfulAuthentication(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, FilterChain filterChain, Authentication authentication) {

            AuthenticateService.addJWTToken(httpServletResponse, authentication);

    }

}
