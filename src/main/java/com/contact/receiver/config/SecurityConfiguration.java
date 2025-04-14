package com.contact.receiver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.contact.receiver.filter.AuthenticationFilter;
import com.contact.receiver.filter.LoginFilter;
import com.contact.receiver.permissions.RoleEnum;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Autowired AuthenticationConfiguration authenticationConfiguration;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/login").permitAll()
            .requestMatchers(HttpMethod.GET, "live").permitAll()
            .requestMatchers(HttpMethod.GET, "/user").authenticated()
            .requestMatchers(HttpMethod.PUT, "/user").authenticated()
            .requestMatchers(HttpMethod.POST, "/user").hasAnyAuthority(RoleEnum.ADMIN.toString())
            .requestMatchers(HttpMethod.DELETE, "/user").hasAnyAuthority(RoleEnum.ADMIN.toString())
            .anyRequest().authenticated();
        });

        http.addFilterBefore(new LoginFilter("/login", authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

}
