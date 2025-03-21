package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/register", "/myprofile", "/userhome", "/updateProfile", 
                                 "/verifyOtp", "/submitProfileUpdate","/fetchCoordinates", 
                                 "/css/**", "/js/**", "/images/**").permitAll() // Public endpoints
                .requestMatchers("/myprofile", "/userhome", "/updateProfile","/verifyOtp", "/submitProfileUpdate","/fetchCoordinates").authenticated() // Protected endpoints
                .anyRequest().authenticated()
//            )
//            .formLogin(form -> form
//                .loginPage("/login") // Custom login page
//                .defaultSuccessUrl("/userhome", true) // Redirect after successful login
//                .permitAll() // Allow everyone to access the login page
//            )
//            .logout(logout -> logout
//                .logoutUrl("/logout") // URL to trigger logout
//                .logoutSuccessUrl("/login") // Redirect to login page after logout
//                .permitAll() // Allow everyone to access the logout page
            );

        return http.build();
    }
}
