package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.CustomUserDetailsService;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll() // Allow public access to these routes
                .anyRequest().authenticated() // Protect all other routes
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login") // Specify the custom login page
                .defaultSuccessUrl("/home", true) // Redirect to home on successful login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Correct logout URL without leading slash
                .logoutSuccessUrl("/") // Redirect to the root ("/") after logout
                .invalidateHttpSession(true) // Invalidate the session on logout
                .clearAuthentication(true) // Clear the authentication details
                .permitAll() // Allow everyone to log out
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }
}